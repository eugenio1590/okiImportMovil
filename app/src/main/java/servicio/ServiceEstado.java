package servicio;

import java.util.List;
import java.util.Map;

import modelo.Estado;

public final class ServiceEstado extends AbstractAsyncTask {

    /**
     * Constructor
     *
     * @param padre : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceEstado(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, Estado.class, closeFrgProgressBar);
    }

    /**METODOS OVERRIDE*/
    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result = consultarEstados(); break;
        default: break;
        }
        return result;
    }

    /**METODOS PROPIOS DE LA CLASE*/
    public Map<String, Object> consultarEstados(){
        Map<String, Object> result = (Map<String, Object>) getToJSON("/gestionMaestros/estados", "", Map.class);
        if(result!=null){
            List<Map<String, Object>> mapEstados = (List<Map<String, Object>>) result.get("estados");
            List<Estado> estados = getToJSONList(Estado.class, mapEstados);
            result.put("estados", estados);
        }
        return result;
    }
}
