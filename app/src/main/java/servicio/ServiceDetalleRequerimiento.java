package servicio;

import java.util.Map;

import modelo.DetalleRequerimiento;

/**
 * Created by Usuario on 01/07/2015.
 */
public class ServiceDetalleRequerimiento extends AbstractAsyncTask {
    /**
     * Constructor
     *
     * @param padre               : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceDetalleRequerimiento(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, DetalleRequerimiento.class, closeFrgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch(id){
            case 1: result = registrarDetalleRequerimiento(params);
        }
        return result;
    }

    private Map<String, Object> registrarDetalleRequerimiento(Map<String, Object> params) {
        Map<String, Object> result = null;
        Integer idRequerimiento = (Integer) params.get("idRequerimiento");
        DetalleRequerimiento detalleRequerimiento = (DetalleRequerimiento) params.get("detalleRequerimiento");
        if(detalleRequerimiento!=null){
            idRequerimiento = (idRequerimiento==null)
                    ? detalleRequerimiento.getRequerimiento().getIdRequerimiento()  : idRequerimiento;
            result = (Map<String, Object>) postToJSON(
                    "/gestionTransacciones/requerimientos/"+idRequerimiento+"/detalleRequerimiento",
                    "", Map.class, detalleRequerimiento);
            Map<String, Object> mapModel = (Map<String, Object>) result.get("detalleRequerimiento");
            detalleRequerimiento = (DetalleRequerimiento) getToJSONObject(DetalleRequerimiento.class, mapModel);
            result.put("detalleRequerimiento", detalleRequerimiento);
        }
        return result;
    }
}
