package app.okiimport.com.okiimport.fragmentos;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import librerias.ActivityGeneric;
import modelo.Requerimiento;
import servicio.ServiceRequerimiento;

public class FrgVerificarRequerimiento extends FrgRequerimiento implements SearchView.OnQueryTextListener {

    public static final String TITULO = EFrgTitulos.FRG_VEFIRICAR_REQUERIMIENTO.getValue();

    //Servicios

    //GUI
    private Spinner spnFVQTipoPersona;
    private SearchView srchFVQCedula;

    public FrgVerificarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_verificar_requerimiento);
    }

    /**EVENTOS*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_frg_verificar_requerimiento, container, false);
        return inflate;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ActivityGeneric.imprimirConsola("EVENTO:", "TextChange");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cedula", "V"+query);
        params.put("pagina", 0);
        params.put("limite", PAGE_SIZE);
        ServiceRequerimiento serviceRequerimiento
                = new ServiceRequerimiento((servicio.AbstractAsyncTask.IComunicatorBackgroundTask) listener, true);
        serviceRequerimiento.execute(2, R.id.srchFVQCedula, params);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText != null && !newText.trim().toLowerCase().equalsIgnoreCase(""))
            srchFVQCedula.setSubmitButtonEnabled(true);
        else
            srchFVQCedula.setSubmitButtonEnabled(false);
        return true;
    }

    @Override
    protected void setListener(View view) {
        spnFVQTipoPersona = (Spinner) view.findViewById(R.id.spnFVQTipoPersona);
        srchFVQCedula = (SearchView) view.findViewById(R.id.srchFVQCedula);

        cargarCombo(R.id.spnFVQTipoPersona, view);

        srchFVQCedula.setOnQueryTextListener(this);
    }

    /**METODOS OVERRIDE*/
    @Override
    protected void setValidator(View view) {

    }

    @Override
    protected void cargarCombo(int id, View view) {
        switch (id){
            case R.id.spnFVQTipoPersona : ActivityGeneric.cargarCombo(R.id.spnFVQTipoPersona, view, llenarTiposPersona()); break;
            default: break;
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
    public void onViewProcesar(Integer idView, Map<String, Object> result){
        switch (idView){
            case R.id.srchFVQCedula: cargarRequerimientos(result); break;
            default: break;
        }
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void cargarRequerimientos(Map<String, Object> result) {
        Integer total = (Integer) result.get("total");
        List<Requerimiento> requerimientos = (List<Requerimiento>) result.get("requerimientos");
        if(total != 0 && !requerimientos.isEmpty()){
            srchFVQCedula.setSubmitButtonEnabled(false);
        }
    }
}