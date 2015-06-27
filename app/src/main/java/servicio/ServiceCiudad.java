package servicio;

import java.util.List;
import java.util.Map;

import librerias.ActivityGeneric;
import modelo.Ciudad;

public final class ServiceCiudad extends AbstractAsyncTask {

    /**
     * Constructor
     *
     * @param padre : Activity que instancio el BackgroundTask
     */
    public ServiceCiudad(IComunicatorBackgroundTask padre) {
        super(padre, Ciudad.class);
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
        ActivityGeneric.imprimirConsola("IdEstado: ", ""+idEstado);
        ActivityGeneric.imprimirConsola("Ruta: ", "/gestionMaestros/estados"+"/"+idEstado+"/ciudades");
        Map<String, Object> result = (Map<String, Object>) getToJSON("/gestionMaestros/estados/"+idEstado+"/ciudades", "", Map.class);
        if(result!=null){
            ActivityGeneric.imprimirConsola("result not null", "");
            List<Map<String, Object>> mapCiudades = (List<Map<String, Object>>) result.get("ciudades");
            ActivityGeneric.imprimirConsola("Lista Size: ", ""+mapCiudades.size());
            List<Ciudad> ciudades = getToJSONList(Ciudad.class, mapCiudades);
            result.put("ciudades", ciudades);
        }
        return result;
    }

}
