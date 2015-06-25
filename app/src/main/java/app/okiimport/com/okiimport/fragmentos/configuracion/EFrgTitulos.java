package app.okiimport.com.okiimport.fragmentos.configuracion;

import java.util.ArrayList;

//Representara los Titulos de los Fragmentos a Usar
public enum EFrgTitulos {

    FRG_REGISTRAR_REQUERIMIENTO("Registrar Requerimiento"),
    FRG_VEFIRICAR_REQUERIMIENTO("Verificar Requerimiento");

    private String value;

    EFrgTitulos(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**METODOS STATICOS DEL ENUM*/
    public static final ArrayList<String> titulosPortalWeb(){
        ArrayList<String> titulos = new  ArrayList<String>();
        titulos.add(FRG_REGISTRAR_REQUERIMIENTO.getValue());
        titulos.add(FRG_VEFIRICAR_REQUERIMIENTO.getValue());
        return titulos;
    }
}
