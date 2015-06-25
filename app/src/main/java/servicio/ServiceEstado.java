package servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Estado;

/**
 * Created by Usuario on 25/06/2015.
 */
public final class ServiceEstado extends AbstractAsyncTask {

    private static ServiceEstado instance;

    /**
     * Constructor
     *
     * @param padre : Activity que instancio el BackgroundTask
     */
    private ServiceEstado(IComunicatorBackgroundTask padre) {
        super(padre, Estado.class);
    }

    public static ServiceEstado getInstance(IComunicatorBackgroundTask padre){
        if(instance==null)
            instance = new ServiceEstado(padre);
        return instance;
    }

    /**METODOS OVERRIDE*/
    @Override
    protected Map<String, Object> doInBackground(Integer id) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result = consultarEstados(); break;
        default: break;
        }
        return result;
    }

    /**METODOS PROPIOS DE LA CLASE*/
    public Map<String, Object> consultarEstados(){
        Map<String, Object> result = new HashMap<String, Object>();
        List<Estado> estados = (List<Estado>) getJSON("/entidadesBasicas/estados", "");
        result.put("estados", estados);
        return result;
    }
}
