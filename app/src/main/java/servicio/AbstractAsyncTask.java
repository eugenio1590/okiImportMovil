package servicio;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.fragmentos.configuracion.FrgProgressBar;
import librerias.ActivityGeneric;

public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Void, Map<String, Object>> {

    protected static final String URL = "http://192.168.1.121:8080/web_service/rest";

    private FrgProgressBar frgProgressBar;

    private IComunicatorBackgroundTask padre;

    protected Integer id;

    protected Integer idComponent;

    protected Class<T> domain;

    protected Map<String, Object> params;

    /**Constructor
     * @param padre: Activity que instancio el BackgroundTask
     * @param domain: Representa la clase del dominio
     * */
    public AbstractAsyncTask(IComunicatorBackgroundTask padre, Class<T> domain) {
        // TODO Auto-generated constructor stub
        this.padre = padre;
        this.domain = domain;
    }

    /**EVENTOS*/
    @Override
    protected void onPreExecute()
    {
        //mostramos el círculo de progreso
        frgProgressBar = new FrgProgressBar();
        padre.showFragment(frgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Void... params) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(id!=null && padre!=null) {
           padre.executePreInBackground(this.id);
           result = doInBackground(this.id, this.params);
           padre.executePostInBackground(this.id);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, Object> result)
    {
        //la tarea en segundo plano ya ha terminado. Ocultamos el progreso.
        try {
            if (frgProgressBar != null)
                frgProgressBar.dismiss();
        }
        catch (Exception e){
            e.printStackTrace();
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
    protected <Y> Y getToJSON(String ruta, String params, Class<?> mapped){
        params = (params==null) ? "" : params;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if(mapped==null)
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, domain);
        else
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, mapped);
    }

    protected  <Y> List<Y> getToJSONList(Class<Y> modelo, List<Map<String, Object>> mapModels){
        List<Y> modelos = new ArrayList<Y>();
        Gson gson = new GsonBuilder().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        Y modeloInstance = null;
        if(mapModels!=null) {
            for (Map<String, Object> mapModel : mapModels) {
                ActivityGeneric.imprimirConsola("Data: ", gson.toJson(mapModel));
                jsonObject = jsonParser.parse(gson.toJson(mapModel)).getAsJsonObject();
                modeloInstance = gson.fromJson(jsonObject, modelo);
                modelos.add(modeloInstance);
            }
        }
        return modelos;
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
        void showFragment(Fragment fragment);
    }
}