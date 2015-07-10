package servicio;

import java.util.List;
import java.util.Map;

import librerias.ActivityGeneric;
import modelo.Ciudad;
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
            case 2: result = consultarRequerimiento(params); break;
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

    private Map<String,Object> consultarRequerimiento(Map params) {
        Map<String, Object> result = null;
        String cedula = (String) params.get("cedula");
        Integer pagina = (Integer) params.get("pagina");
        Integer limite = (Integer) params.get("limite");
        if(cedula!=null && pagina!=null && limite!=null) {
            result = (Map<String, Object>) getToJSON("/gestionTransacciones/requerimientos/clientes/" + cedula ,
                    "pagina="+pagina+"&limite="+limite, Map.class);
            if (result != null) {
                List<Map<String, Object>> mapRequerimiento = (List<Map<String, Object>>) result.get("requerimientos");
                Map<String, Object> mapTotal = (Map<String, Object>) result.get("total");
                List<Requerimiento> requerimientos = getToJSONList(Requerimiento.class, mapRequerimiento);
                Integer total = Integer.valueOf((String) getToJSONObject(String.class, mapTotal));
                ActivityGeneric.imprimirConsola("Total", ""+total);
                ActivityGeneric.imprimirConsola("Reqm:", ""+requerimientos.size());
                result.put("total", total);
                result.put("requerimientos", requerimientos);
            }
        }
        return result;
    }
}
