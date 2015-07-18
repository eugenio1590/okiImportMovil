package app.okiimport.com.okiimport;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.okiimport.com.okiimport.fragmentos.*;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import librerias.componentes.Fragmento;
import librerias.componentes.IComunicacionListener;
import servicio.AbstractAsyncTask;
import servicio.AbstractAsyncTask.IComunicatorBackgroundTask;

import java.util.ArrayList;
import java.util.Map;

public class ActInicio extends ActRequerimiento implements IComunicacionListener, IComunicatorBackgroundTask {

    //GUI
    private LinearLayout llARQInicio;
    private TextView txtARQMsjError;

    /**CONSTRUCTOR*/
    public ActInicio() {
        super(R.id.navigation_drawer, R.id.drawer_layout, R.menu.act_inicio);
        this.fragmentos = new ArrayList<String>();
        this.fragmentos.addAll(EFrgTitulos.titulosPortalWeb());
    }

    /**EVENTOS*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_inicio);
        super.createNavigation();

        llARQInicio = (LinearLayout) findViewById(R.id.llARQInicio);
        txtARQMsjError = (TextView) findViewById(R.id.txtARQMsjError);
        if(!isConnectNetwork())
            configurarTextoMensaje(AbstractAsyncTask.MsjError.TIME_OUT.getTexto(), LinearLayout.VISIBLE);
    }

    @Override
    protected void cargarCombo(int id) {

    }

    /**METODOS OVERRIDE*/
    @Override
    public Fragmento newInstanceFragment(Integer position) {
        switch(position){
            case 1: return new FrgRegistrarRequerimiento();
            case 2: return new FrgVerificarRequerimiento();

            default: return null;
        }
    }

    /**INTERFACE*/
    //1. IComunicacionListener
    @Override
    public void onClickBoton(View v) {

    }

    @Override
    public void onClickSelected(String obj, int pos) {

    }

    @Override
    public void cancelar() {

    }

    //2. IComunicatorBackgroundTask
    @Override
    public String executePreInBackground(Integer id) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(llARQInicio!=null)
                    configurarTextoMensaje(null, LinearLayout.INVISIBLE);
            }
        });
        return null;
    }

    @Override
    public String executePostInBackground(Integer id) {
        return null;
    }

    @Override
    public void executeOnPostExecute(Map<String, Object> result) {
        ((FrgRequerimiento)this.fragmento).onViewProcesar((Integer) result.get("idComponent"), result);
    }

    @Override
    public void canceledOnExecute(Integer id, final Exception e){
        //Se mostrara un mensaje mejor.
        runOnUiThread(new Runnable(){
            public void run() {
                if (llARQInicio != null && txtARQMsjError != null)
                    configurarTextoMensaje(e.getMessage(), LinearLayout.VISIBLE);
            }
        });
    }

    @Override
    public boolean isConnectNetwork(){
        return super.isConnectNetwork();
    }

    @Override
    public void showFragment(Fragmento fragmento) {
        FragmentManager fm = getSupportFragmentManager();
        fragmento.show(fm, "fragment_"+Math.random());
    }

    /**METODOS OVERRIDE*/
    @Override
    public <T> T getGeneric(int id, Class<T> tClass) {
        return null;
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

    @Override
    public void setListeners() {

    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void configurarTextoMensaje(String texto, int visible){
        if(visible == LinearLayout.VISIBLE){
            llARQInicio.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llARQInicio.setVisibility(visible);
            txtARQMsjError.setText(texto);
        }
        else if(visible == LinearLayout.INVISIBLE){
            llARQInicio.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0*LinearLayout.LayoutParams.WRAP_CONTENT));
            llARQInicio.setVisibility(visible);
        }
    }
}
