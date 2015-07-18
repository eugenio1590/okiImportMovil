package app.okiimport.com.okiimport.fragmentos;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import java.util.Map;
import java.util.Vector;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.TxtValidatorNoEmpty;
import conexion.IConexionDAO;
import librerias.componentes.Fragmento;
import librerias.componentes.ViewValidator;

//Clase base para los fragmentos del Navigation
public abstract class FrgRequerimiento extends Fragmento {

    protected static final Integer PAGE_SIZE = 10;

    protected OnFragmentInteractionListener mListener;

    protected static Vector<IConexionDAO.ObjetosCombo> tiposPersona;

    /**
     * Constructor de la Clase
     *
     * @param titulo
     * @param layout
     */
    public FrgRequerimiento(String titulo, int layout) {
        super(titulo, layout);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            ((app.okiimport.com.okiimport.FrgRequerimiento) activity).setmTitle(titulo);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**METODOS ABSTRACTOS DE LA CLASE*/
    public abstract void onViewProcesar(Integer idView, Map<String, Object> result);

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void onShowFragment(Fragmento fragmento);
    }

    /**METODOS PROPIOS DE LA CLASE*/
    protected ViewValidator findViewValidator(Integer id, View view){
        ViewValidator viewValidator = (ViewValidator) view.findViewById(id);
        viewValidator.setImageResource(R.drawable.warning);
        viewValidator.setImageBackgroundColor(Color.TRANSPARENT);
        return viewValidator;
    }

    protected TxtValidatorNoEmpty addTxtValidatorNotEmpty(View view, int id,
            TxtValidatorNoEmpty validator, ViewValidator viewValidator){
        EditText txtView = (EditText) view.findViewById(id);
        txtView.addTextChangedListener(validator = new TxtValidatorNoEmpty(txtView, viewValidator));
        return validator;
    }

    /**METODOS ESTATICOS DE LA CLASE*/
    public static Vector<IConexionDAO.ObjetosCombo> llenarTiposPersona(){
        tiposPersona = new Vector<IConexionDAO.ObjetosCombo>();
        tiposPersona.add(new IConexionDAO.ObjetosCombo(true, "V"));
        tiposPersona.add(new IConexionDAO.ObjetosCombo(false, "J"));
        return tiposPersona;
    }
}
