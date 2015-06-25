package app.okiimport.com.okiimport.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import app.okiimport.com.okiimport.fragmentos.configuracion.FrgProgressBar;
import librerias.ActivityGeneric;

public class FrgRegistrarRequerimiento extends FrgRequerimiento {

    public static final String TITULO = EFrgTitulos.FRG_REGISTRAR_REQUERIMIENTO.getValue();

    //GUI
    private Spinner spnFRQTipoPersona;
    private Button btnFRQSiguiente;

    //Fragmentos
    private FrgProgressBar frgProgressBar;

    public FrgRegistrarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_registrar_requerimiento);
        ActivityGeneric.imprimirConsola("Paso", "Consola");
    }

    /**EVENTOS*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFRQSiguiente : {
                frgProgressBar = new FrgProgressBar();
                ((OnFragmentInteractionListener) listener).onShowFragment(frgProgressBar);
            }; break;

            default:break;
        }
    }

    /**METODOS OVERRIDE*/
    @Override
    protected void setListener(View view) {
        spnFRQTipoPersona = (Spinner) view.findViewById(R.id.spnFRQTipoPersona);
        btnFRQSiguiente = (Button) view.findViewById(R.id.btnFRQSiguiente);
        ActivityGeneric.cargarCombo(R.id.spnFRQTipoPersona, view, llenarTiposPersona());
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
}