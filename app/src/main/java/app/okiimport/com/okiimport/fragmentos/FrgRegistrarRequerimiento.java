package app.okiimport.com.okiimport.fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.okiimport.com.okiimport.R;

public class FrgRegistrarRequerimiento extends FrgRequerimiento {

    public static final String TITULO = EFrgTitulos.FRG_REGISTRAR_REQUERIMIENTO.getValue();

    public FrgRegistrarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_registrar_requerimiento);
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