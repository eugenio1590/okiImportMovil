package servicio;

import java.util.Map;

import modelo.Requerimiento;

/**
 * Created by Usuario on 28/06/2015.
 */
public class ServiceRequerimiento extends AbstractAsyncTask {
    /**
     * Constructor
     *
     * @param padre               : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceRequerimiento(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, Requerimiento.class, closeFrgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1 : result = registrarRequerimiento(params); break;
        }
        return result;
    }

    private Map<String, Object> registrarRequerimiento(Map<String, Object> params) {
        Map<String, Object> result = null;
        Requerimiento requerimiento = (Requerimiento) params.get("requerimiento");
        if(requerimiento!=null){
            result = (Map<String, Object>) postToJSON("/gestionTransacciones/requerimientos", "", Map.class, requerimiento);
            Map<String, Object> mapModel = (Map<String, Object>) result.get("requerimiento");
            requerimiento = (Requerimiento) getToJSONObject(Requerimiento.class, mapModel);
            result.put("requerimiento", requerimiento);
        }
        return result;
    }
}
