package app.okiimport.com.okiimport.fragmentos;

import android.app.Activity;
import android.net.Uri;

import app.okiimport.com.okiimport.ActRequerimiento;
import librerias.componentes.Fragmento;

//Clase base para los fragmentos del Navigation
public abstract class FrgRequerimiento extends Fragmento {

    private OnFragmentInteractionListener mListener;

    /**
     * Constructor de la Clase
     *
     * @param titulo
     * @param layout
     */
    public FrgRequerimiento(String titulo, int layout) {
        super(titulo, layout);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            ((ActRequerimiento) activity).setmTitle(titulo);
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
    }
}