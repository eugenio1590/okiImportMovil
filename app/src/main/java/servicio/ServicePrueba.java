package servicio;

import java.util.List;
import java.util.Map;

import modelo.Ciudad;
import modelo.Estado;

/**
 * Created by Usuario on 27/06/2015.
 */
public class ServicePrueba extends AbstractAsyncTask {
    /**
     * Constructor
     *
     * @param padre  : Activity que instancio el BackgroundTask
     */
    public ServicePrueba(IComunicatorBackgroundTask padre) {
        super(padre, Estado.class);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result=consultarEstadosActivos((String)params.get("cedula")); break;
        }
        return result;
    }

    private Map<String, Object> consultarEstadosActivos(String cedula){
        Map<String, Object> result = (Map<String, Object>) getToJSON("/gestionMaestros/estados", "", Map.class);
        if(result!=null){
            Long total = Long.valueOf((String) result.get("total"));
            List<Map<String, Object>> mapEstados = (List<Map<String, Object>>) result.get("estados");
            List<Estado> estados = getToJSONList(Estado.class, mapEstados);
            result.put("estados", estados);
        }
        return result;
    }
}
