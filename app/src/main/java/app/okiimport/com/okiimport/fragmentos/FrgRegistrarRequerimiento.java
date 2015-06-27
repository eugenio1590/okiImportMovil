package app.okiimport.com.okiimport.fragmentos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import app.okiimport.com.okiimport.fragmentos.configuracion.FrgProgressBar;
import conexion.IConexionDAO.ObjetosCombo;
import librerias.ActivityGeneric;
import modelo.Ciudad;
import modelo.Estado;
import servicio.AbstractAsyncTask.IComunicatorBackgroundTask;
import servicio.ServiceCiudad;
import servicio.ServiceEstado;


public class FrgRegistrarRequerimiento extends FrgRequerimiento implements OnItemSelectedListener{

    public static final String TITULO = EFrgTitulos.FRG_REGISTRAR_REQUERIMIENTO.getValue();

    //Servicios
    private ServiceEstado serviceEstado;
    private ServiceCiudad serviceCiudad;

    //GUI
    private Spinner spnFRQTipoPersona;
    private Spinner spnFRQEstado;
    private Button btnFRQSiguiente;

    //Fragmentos
    private FrgProgressBar frgProgressBar;

    //Modelos
    private List<Estado> estados;
    private List<Ciudad> ciudades;

    private Estado estado;

    public FrgRegistrarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_registrar_requerimiento);
        ActivityGeneric.imprimirConsola("Paso", "Consola");
    }

    /**EVENTOS*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceEstado = new ServiceEstado((IComunicatorBackgroundTask) listener);
        serviceEstado.execute(1, R.id.spnFRQEstado);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFRQSiguiente : {
                frgProgressBar = new FrgProgressBar();
                ((OnFragmentInteractionListener) getActivity()).onShowFragment(frgProgressBar);
            }; break;

            default:break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ObjetosCombo item = (ObjetosCombo) ((Spinner) parent).getItemAtPosition(position);
        Map<String, Object> params = null;
        ActivityGeneric.imprimirConsola("Paso Listener", ""+(view.getId()==R.id.spnFRQCiudad));
        switch (((Spinner) parent).getId()){
            case R.id.spnFRQEstado: {
                params = new HashMap<String, Object>();
                params.put("idEstado", String.valueOf(item.getId())); //De cargarse la variable estado
                serviceCiudad = new ServiceCiudad((IComunicatorBackgroundTask) listener);
                serviceCiudad.execute(1, R.id.spnFRQCiudad, params);
            } break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**METODOS OVERRIDE*/
    @Override
    protected void setListener(View view) {
        spnFRQTipoPersona = (Spinner) view.findViewById(R.id.spnFRQTipoPersona);
        spnFRQEstado = (Spinner) view.findViewById(R.id.spnFRQEstado);
        btnFRQSiguiente = (Button) view.findViewById(R.id.btnFRQSiguiente);

        ActivityGeneric.cargarCombo(R.id.spnFRQTipoPersona, view, llenarTiposPersona());

        spnFRQEstado.setOnItemSelectedListener(this);
        btnFRQSiguiente.setOnClickListener(this);
    }

    @Override
    protected void cargarCombo(int id, View view) {

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
        ActivityGeneric.imprimirConsola("ID VIEW:", ""+idView);
        switch (idView){
            case R.id.spnFRQEstado: cargarListaEstado(result); break;
            case R.id.spnFRQCiudad: cargarListaCiudades(result); break;
            default: break;
        }
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void cargarListaEstado(Map<String, Object> result){
        estados = (List<Estado>) result.get("estados");
        Vector<ObjetosCombo> estadosCombo = new Vector<ObjetosCombo>();

        for(Estado estado : estados)
            estadosCombo.add(new ObjetosCombo(estado.getIdEstado(), estado.getNombre()));

        ActivityGeneric.cargarCombo(R.id.spnFRQEstado, getView(), estadosCombo);
    }

    private void cargarListaCiudades(Map<String, Object> result){
        ciudades = (List<Ciudad>) result.get("ciudades");
        Vector<ObjetosCombo> ciudadesCombo = new Vector<ObjetosCombo>();

        for(Ciudad ciudad : ciudades)
            ciudadesCombo.add(new ObjetosCombo(ciudad.getIdCiudad(), ciudad.getNombre()));

        ActivityGeneric.cargarCombo(R.id.spnFRQCiudad, getView(), ciudadesCombo);
    }

}