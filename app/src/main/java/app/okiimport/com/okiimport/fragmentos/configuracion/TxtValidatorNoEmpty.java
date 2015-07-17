package app.okiimport.com.okiimport.fragmentos.configuracion;

import android.widget.EditText;

import app.okiimport.com.okiimport.R;
import librerias.componentes.ViewValidator;

public class TxtValidatorNoEmpty extends ViewValidator.TxtValidator {

    public TxtValidatorNoEmpty(EditText editText, ViewValidator viewValidator) {
        super(editText, viewValidator, R.drawable.edittext_error);
    }

    @Override
    public boolean validateAfterChange(EditText editText, String text) {
        if(text.trim().equalsIgnoreCase("") ){
            error = "Campo obligatorio";
            return false;
        }
        return true;
    }
}
