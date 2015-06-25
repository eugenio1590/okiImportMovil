package app.okiimport.com.okiimport.fragmentos.configuracion;



import android.app.Fragment;
import android.view.View;

import app.okiimport.com.okiimport.R;
import librerias.componentes.Fragmento;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FrgProgressBar extends Fragmento {


    public FrgProgressBar() {
        // Required empty public constructor
        super("Cargando...", R.layout.fragment_frg_progress_bar);
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
