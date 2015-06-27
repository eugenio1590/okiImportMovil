package app.okiimport.com.okiimport.fragmentos;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.R;
import librerias.ActivityGeneric;
import modelo.Estado;
import servicio.ServicePrueba;


/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class FrgPrueba extends FrgRequerimiento {


    public FrgPrueba() {
        // Required empty public constructor
        super("Prueba", R.layout.fragment_frg_prueba);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_prueba, container, false);
    }

    @Override
    protected void setListener(View view) {
        Button btnPrueba = (Button) view.findViewById(R.id.btnPrueba);
        btnPrueba.setOnClickListener(this);
    }

    @Override
    protected void cargarCombo(int id, View view) {

    }


    @Override
    public void onViewProcesar(Integer idView, Map<String, Object> result) {
        switch (idView){
            case R.id.btnPrueba: consumirResultados(result); break;
            case 80: consumirResultados(result); break;
        }
    }

    @Override
    public boolean validarFormulario() {
        return false;
    }

    @Override
    public void limpiar() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPrueba: mostrarMensaje("Titulo" , "Nuevo Mensaje"); break;
        }
    }

    private void mostrarMensaje(String titulo, String mensaje){
        ActivityGeneric.imprimirConsola(titulo, mensaje);
        ServicePrueba servicePrueba = new ServicePrueba((servicio.AbstractAsyncTask.IComunicatorBackgroundTask) this.getActivity());
        servicePrueba.execute(1, R.id.btnPrueba);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cedula", "20186243");
        servicePrueba.execute(1, 80, params);
    }

    protected void consumirResultados(Map<String, Object> result){
        List<Estado> estados = (List<Estado>) result.get("estados");
    }
}
