package servicio;

import java.util.List;
import java.util.Map;

import modelo.MarcaVehiculo;

public final class ServiceMarcaVehiculo extends AbstractAsyncTask {
    /**
     * Constructor
     *
     * @param padre  : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceMarcaVehiculo(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, MarcaVehiculo.class, closeFrgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result=consultarMarcasVehiculos(); break;
        }
        return result;
    }

    private Map<String, Object> consultarMarcasVehiculos(){
        Map<String, Object> result = (Map<String, Object>) getToJSON("/gestionMaestros/marcas/vehiculos", "", Map.class);
        if(result!=null){
            List<Map<String, Object>> mapModels = (List<Map<String, Object>>) result.get("marcas");
            List<MarcaVehiculo> marcasVehiculo = getToJSONList(MarcaVehiculo.class, mapModels);
            result.put("marcas", marcasVehiculo);
        }
        return result;
    }
}
