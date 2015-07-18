package servicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import librerias.ActivityGeneric;
import librerias.componentes.Fragmento;

public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Void, Map<String, Object>> {

    protected static final String URL = "http://192.168.1.114:8080/web_service/rest";

    private static ProgressDialog frgProgressBar;

    private IComunicatorBackgroundTask padre;

    protected Integer id;

    protected Integer idComponent;

    protected Class<T> domain;

    protected Map<String, Object> params;

    protected boolean closeFrgProgressBar;

    /**Constructor
     * @param padre: Activity que instancio el BackgroundTask
     * @param domain: Representa la clase del dominio
     * */
    public AbstractAsyncTask(IComunicatorBackgroundTask padre, Class<T> domain, boolean closeFrgProgressBar) {
        // TODO Auto-generated constructor stub
        this.padre = padre;
        this.domain = domain;
        this.closeFrgProgressBar = closeFrgProgressBar;
    }

    /**EVENTOS*/
    @Override
    protected void onPreExecute()
    {
        //mostramos el círculo de progreso
        if(frgProgressBar ==null) {
            frgProgressBar = ProgressDialog.show((Context) padre, "", "Loading...");
        }
    }

    @Override
    protected Map<String, Object> doInBackground(Void... params) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(id!=null && padre!=null) {
            try {
                padre.executePreInBackground(this.id);
                result = doInBackground(this.id, this.params);
                padre.executePostInBackground(this.id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("Error in Service", e.toString());
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, Object> result)
    {
        //la tarea en segundo plano ya ha terminado. Ocultamos el progreso.
        closeFrgProgressBar();

        if(result==null)
            result = new HashMap<String, Object>();

        if(padre!=null) {
            result.put("idComponent", this.idComponent);
            padre.executeOnPostExecute(result);
        }
    }

    public void execute(Integer id, Integer idComponent){
        setId(id);
        setIdComponent(idComponent);
        super.execute();
        setTimeOut();
    }

    public void execute(Integer id, Integer idComponent, Map<String, Object> params){
        this.params = params;
        this.execute(id, idComponent);
        setTimeOut();
    }

    /**METODOS ABSTRACTOS DE LA CLASE*/
    protected abstract Map<String, Object> doInBackground(Integer id, Map<String, Object> params);

    /**METODOS PROPIOS DE LA CLASE*/
    //GENERAL
    private void closeFrgProgressBar(){
        try {
            if(frgProgressBar !=null && closeFrgProgressBar){
                frgProgressBar.dismiss();
                frgProgressBar = null;
            }
        }
        catch (Exception e){
            Log.e("Error in ProgressBar", e.toString());
        }
    }

    private void setTimeOut(){
        final AbstractAsyncTask me = this;
        Thread thread = new Thread(){
            public void run(){
                try {
                    me.get(30000, TimeUnit.MILLISECONDS);  //set time in milisecond(in this timeout is 30 seconds

                } catch (Exception e) {
                    me.closeFrgProgressBar();
                    padre.canceledOnExecute(me.id);
                    me.cancel(true);
                    Log.e("Error:", e.toString());
                }
            }
        };
        thread.start();
    }

    //GET
    protected <Y> Y getToJSON(String ruta, String params, Class<?> mapped){
        params = (params==null) ? "" : params;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if(mapped==null)
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, domain);
        else
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, mapped);
    }

    //POST
    protected <Y, X> Y postToJSON(String ruta, String params, Class mapped, X data){
        params = (params==null) ? "" : params;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "Close");
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        requestHeaders.setContentType(new MediaType("application","json"));

        HttpEntity<X> requestEntity = new HttpEntity<X>(data, requestHeaders);
        ResponseEntity<X> responseEntity = restTemplate.exchange(URL+ruta+"?"+params, HttpMethod.POST, requestEntity, mapped);
        return (Y) responseEntity.getBody();
    }

    protected  <Y> List<Y> getToJSONList(Class<Y> modelo, List<Map<String, Object>> mapModels){
        List<Y> modelos = new ArrayList<Y>();
        if(mapModels!=null) {
            for (Map<String, Object> mapModel : mapModels)
                modelos.add(getToJSONObject(modelo, mapModel));
        }
        return modelos;
    }

    protected <Y> Y getToJSONObject(Class<Y> modelo, Map<String, Object> mapModel){
        JsonParser jsonParser = new JsonParser();
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
                .setDateFormat("yyyy-mm-dd HH:mm:ss").create();
        ActivityGeneric.imprimirConsola("Data: ", gson.toJson(mapModel));
        JsonObject jsonObject = jsonParser.parse(gson.toJson(mapModel)).getAsJsonObject();
        return gson.fromJson(jsonObject, modelo);
    }

    /**GETTERS Y SETTERS*/
    public void setId(Integer id){
        this.id=id;
    }

    public Integer getId(){
        return id;
    }

    public Integer getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public IComunicatorBackgroundTask getPadre() {
        return padre;
    }

    public void setPadre(IComunicatorBackgroundTask padre) {
        this.padre = padre;
    }

    //Interfaz de Comunicacion
    public interface IComunicatorBackgroundTask{
        String executePreInBackground(Integer id);
        String executePostInBackground(Integer id);
        void executeOnPostExecute(Map<String, Object> result);
        void canceledOnExecute(Integer id);
        void showFragment(Fragmento fragmento);
    }

    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

        @Override
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }
}
