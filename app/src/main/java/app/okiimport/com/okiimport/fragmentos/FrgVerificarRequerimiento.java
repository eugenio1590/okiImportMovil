package app.okiimport.com.okiimport.fragmentos;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.AdptRequerimiento;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import conexion.IConexionDAO;
import librerias.ActivityGeneric;
import modelo.Requerimiento;
import servicio.ServiceRequerimiento;

public class FrgVerificarRequerimiento extends FrgRequerimiento implements SearchView.OnQueryTextListener, AbsListView.OnScrollListener {

    public static final String TITULO = EFrgTitulos.FRG_VEFIRICAR_REQUERIMIENTO.getValue();

    //Servicios

    //GUI
    private Spinner spnFVQTipoPersona;
    private SearchView srchFVQCedula;
    private ListView lstFVQRequerimientos;
    private LinearLayout lnlHeadRequerimientos;

    //Atributos
    private Integer totalRequerimientos;
    private Boolean loading = false;

    public FrgVerificarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_verificar_requerimiento);
    }

    /**EVENTOS*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_frg_verificar_requerimiento, container, false);
        return inflate;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        lstFVQRequerimientos.setAdapter(new AdptRequerimiento(getActivity(), new ArrayList<Requerimiento>()));
        cambiarRequerimientos(query, 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText != null && !newText.trim().toLowerCase().equalsIgnoreCase(""))
            srchFVQCedula.setSubmitButtonEnabled(true);
        else
            srchFVQCedula.setSubmitButtonEnabled(false);
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(view!=null) {
            boolean lastItem = (firstVisibleItem + visibleItemCount == totalItemCount);
            switch (view.getId()) {
                case R.id.lstFVQRequerimientos:
                    paginarRequerimiento(lastItem);
                    break;
                default:
                    break;
            }
        }
    }

    /**METODOS OVERRIDE*/
    @Override
    protected void setListener(View view) {
        spnFVQTipoPersona = (Spinner) view.findViewById(R.id.spnFVQTipoPersona);
        srchFVQCedula = (SearchView) view.findViewById(R.id.srchFVQCedula);
        lstFVQRequerimientos = (ListView) view.findViewById(R.id.lstFVQRequerimientos);
        lnlHeadRequerimientos = (LinearLayout) view.findViewById(R.id.lnlHeadRequerimientos);

        cargarCombo(R.id.spnFVQTipoPersona, view);

        srchFVQCedula.setOnQueryTextListener(this);
        lstFVQRequerimientos.setOnScrollListener(this);
    }

    @Override
    protected void setValidator(View view) {

    }

    @Override
    protected void cargarCombo(int id, View view) {
        switch (id){
            case R.id.spnFVQTipoPersona : ActivityGeneric.cargarCombo(R.id.spnFVQTipoPersona, view, llenarTiposPersona()); break;
            default: break;
        }
    }

    @Override
    public boolean validarFormulario() {
        return false;
    }

    @Override
    public void limpiar() {

    }

    @Override
    public void onViewProcesar(Integer idView, Map<String, Object> result){
        switch (idView){
            case R.id.srchFVQCedula: cargarRequerimientos(result); break;
            default: break;
        }
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void cambiarRequerimientos(String query, int pagina){
        IConexionDAO.ObjetosCombo tipoPersona = (IConexionDAO.ObjetosCombo) spnFVQTipoPersona.getSelectedItem();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cedula", tipoPersona.toString()+query);
        params.put("pagina", pagina);
        params.put("limite", PAGE_SIZE);
        ServiceRequerimiento serviceRequerimiento
                = new ServiceRequerimiento((servicio.AbstractAsyncTask.IComunicatorBackgroundTask) listener, true);
        serviceRequerimiento.execute(2, R.id.srchFVQCedula, params);
    }

    private void paginarRequerimiento(boolean lastItem) {
        String query = srchFVQCedula.getQuery().toString();
        if(totalRequerimientos!=null){
            Integer totalItemCount = lstFVQRequerimientos.getAdapter().getCount();
            boolean moreRows = totalItemCount < totalRequerimientos;
            if(lastItem && moreRows && !loading && totalItemCount!=0) {
                loading = true;
                cambiarRequerimientos(query, Integer.valueOf(totalRequerimientos / totalItemCount));
            }
        }
    }

    private void cargarRequerimientos(Map<String, Object> result) {
        totalRequerimientos = (Integer) result.get("total");
        List<Requerimiento> requerimientos = (List<Requerimiento>) result.get("requerimientos");

        if(totalRequerimientos != 0 && !requerimientos.isEmpty()){
            srchFVQCedula.setSubmitButtonEnabled(false);
            ListAdapter adapter = lstFVQRequerimientos.getAdapter();
            if(adapter==null)
                lstFVQRequerimientos.setAdapter(new AdptRequerimiento(getActivity(), requerimientos));
            else if(adapter instanceof AdptRequerimiento) {
                for(Requerimiento requerimiento : requerimientos)
                    ((AdptRequerimiento) adapter).add(requerimiento);
                ((AdptRequerimiento) adapter).notifyDataSetChanged();
            }
            lnlHeadRequerimientos.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            lnlHeadRequerimientos.setVisibility(LinearLayout.INVISIBLE);
            lstFVQRequerimientos.setAdapter(new AdptRequerimiento(getActivity(), requerimientos));
        }

        loading = false;
    }


}