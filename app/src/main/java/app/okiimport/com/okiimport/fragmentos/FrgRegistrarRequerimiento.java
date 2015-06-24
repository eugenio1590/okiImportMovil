package app.okiimport.com.okiimport.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import app.okiimport.com.okiimport.R;
import librerias.ActivityGeneric;

public class FrgRegistrarRequerimiento extends FrgRequerimiento {

    public static final String TITULO = EFrgTitulos.FRG_REGISTRAR_REQUERIMIENTO.getValue();

    private Spinner spnFRQTipoPersona;

    public FrgRegistrarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_registrar_requerimiento);
        ActivityGeneric.imprimirConsola("Paso", "Consola");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_registrar_requerimiento, container, false);
    }

    @Override
    protected void setListener(View view) {
        spnFRQTipoPersona = (Spinner) view.findViewById(R.id.spnFRQTipoPersona);
        ActivityGeneric.cargarCombo(R.id.spnFRQTipoPersona, view, llenarTiposPersona());
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
    public void onClick(View v) {

    }
}