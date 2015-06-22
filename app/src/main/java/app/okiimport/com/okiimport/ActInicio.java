package app.okiimport.com.okiimport;

import android.os.Bundle;

import app.okiimport.com.okiimport.fragmentos.*;
import librerias.componentes.Fragmento;

import java.util.ArrayList;

public class ActInicio extends ActRequerimiento {

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

    /**METODOS OVERRIDE*/
    @Override
    public Fragmento newInstanceFragment(Integer position) {
        switch(position){
            case 1: return new FrgRegistrarRequerimiento();
            case 2: return new FrgVerificarRequerimiento();

            default: return null;
        }
    }
}
