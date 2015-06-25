package servicio;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.fragmentos.configuracion.FrgProgressBar;

public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Void, Map<String, Object>> {

    protected static final String URL = "";

    private FrgProgressBar frgProgressBar;

    private IComunicatorBackgroundTask padre;

    protected Integer id;

    protected Integer idComponent;

    protected Class<T> domain;

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
           result = doInBackground(this.id);
           padre.executePostInBackground(this.id);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, Object> result)
    {
        //la tarea en segundo plano ya ha terminado. Ocultamos el progreso.
        frgProgressBar.dismiss();

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

    /**METODOS ABSTRACTOS DE LA CLASE*/
    protected abstract Map<String, Object> doInBackground(Integer id);

    /**METODOS PROPIOS DE LA CLASE*/
    protected <Y> Y getJSON(String ruta, String params, boolean lista){
        params = (params==null) ? "" : params;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if(!lista)
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, domain);
        else
            return (Y) restTemplate.getForObject(URL+ruta+"?"+params, List.class);
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
