package servicio;

import java.util.Map;

import modelo.Cliente;

/**
 * Created by Usuario on 28/06/2015.
 */
public class ServiceCliente extends AbstractAsyncTask {
    /**
     * Constructor
     *
     * @param padre               : Activity que instancio el BackgroundTask
     * @param closeFrgProgressBar
     */
    public ServiceCliente(IComunicatorBackgroundTask padre, boolean closeFrgProgressBar) {
        super(padre, Cliente.class, closeFrgProgressBar);
    }

    @Override
    protected Map<String, Object> doInBackground(Integer id, Map params) {
        Map<String, Object> result = null;
        switch (id){
            case 1: result=registrarOActualizarCliente(params); break;
        }
        return result;
    }

    private Map<String,Object> registrarOActualizarCliente(Map params) {
        Map<String, Object> result = null;
        Cliente cliente = (Cliente) params.get("cliente");
        if(cliente!=null){
            result = (Map<String, Object>) postToJSON("/gestionMaestros/clientes", "", Map.class, cliente);
            Map<String, Object> mapModel = (Map<String, Object>) result.get("cliente");
            cliente = (Cliente) getToJSONObject(Cliente.class, mapModel);
            result.put("cliente", cliente);
        }
        return result;
    }
}
