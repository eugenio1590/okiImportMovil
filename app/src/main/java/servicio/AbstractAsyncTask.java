package servicio;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.fragmentos.configuracion.FrgProgressBar;
import librerias.ActivityGeneric;
import librerias.componentes.Fragmento;

public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Void, Map<String, Object>> {

    protected static final String URL = "http://192.168.1.121:8080/web_service/rest";

    private static FrgProgressBar frgProgressBar;

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
        if(frgProgressBar==null) {
            frgProgressBar = new FrgProgressBar();
            padre.showFragment(frgProgressBar);
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
                Log.e("Error in Service", e.toString());
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, Object> result)
    {
        //la tarea en segundo plano ya ha terminado. Ocultamos el progreso.
        try {
            if (frgProgressBar != null && closeFrgProgressBar) {
                frgProgressBar.dismiss();
                frgProgressBar = null;
            }
        }
        catch (Exception e){
            Log.e("Error in ProgressBar", e.toString());
        }

        if(padre!=null) {
            result.put("idComponent", this.idComponent);
            padre.executeOnPostExecute(result);
        }
    }

    public void execute(Integer id, Integer idComponent){
        setId(id);
        setIdComponent(idComponent);
        super.execute();
    }

    public void execute(Integer id, Integer idComponent, Map<String, Object> params){
        this.params = params;
        this.execute(id, idComponent);
    }

    /**METODOS ABSTRACTOS DE LA CLASE*/
    protected abstract Map<String, Object> doInBackground(Integer id, Map<String, Object> params);

    /**METODOS PROPIOS DE LA CLASE*/
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

        HttpHeaders requestHeaders = new HttpHeaders();
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
        Gson gson = new GsonBuilder().create();
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
        void showFragment(Fragmento fragmento);
    }
}
