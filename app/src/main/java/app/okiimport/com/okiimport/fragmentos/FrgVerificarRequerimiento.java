package app.okiimport.com.okiimport.fragmentos;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.okiimport.com.okiimport.R;


public class FrgVerificarRequerimiento extends FrgRequerimiento {

    public static final String TITULO = EFrgTitulos.FRG_VEFIRICAR_REQUERIMIENTO.getValue();

    public FrgVerificarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_verificar_requerimiento);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_verificar_requerimiento, container, false);
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