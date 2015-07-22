package servicio;

import java.util.List;
import java.util.Map;

import modelo.Ciudad;

public final class ServiceCiudad extends AbstractAsyncTask {

    /**
     * Constructor
     *
     * @param padre : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceCiudad(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, Ciudad.class, closeFrgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result = consultarCiudades(Integer.valueOf((String)params.get("idEstado"))); break;
            default: break;
        }
        return result;
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private Map<String, Object> consultarCiudades(Integer idEstado){
        Map<String, Object> result = (Map<String, Object>) getToJSON("/gestionMaestros/estados/"+idEstado+"/ciudades", "", Map.class);
        if(result!=null){
            List<Map<String, Object>> mapCiudades = (List<Map<String, Object>>) result.get("ciudades");
            List<Ciudad> ciudades = getToJSONList(Ciudad.class, mapCiudades);
            result.put("ciudades", ciudades);
        }
        return result;
    }

}
