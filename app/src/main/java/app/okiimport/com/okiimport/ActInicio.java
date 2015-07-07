package app.okiimport.com.okiimport;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import app.okiimport.com.okiimport.fragmentos.*;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import librerias.componentes.Fragmento;
import librerias.componentes.IComunicacionListener;
import servicio.AbstractAsyncTask.IComunicatorBackgroundTask;

import java.util.ArrayList;
import java.util.Map;

public class ActInicio extends ActRequerimiento implements IComunicacionListener, IComunicatorBackgroundTask {

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
    public void showFragment(Fragmento fragmento) {
        FragmentManager fm = getSupportFragmentManager();
        fragmento.show(fm, "fragment_"+Math.random());
    }

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
}
