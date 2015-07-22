package app.okiimport.com.okiimport;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.internal.util.Predicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.okiimport.com.okiimport.fragmentos.FrgRequerimiento;
import conexion.IConexionDAO;
import librerias.componentes.Fragmento;
import modelo.Cliente;
import modelo.DetalleRequerimiento;
import modelo.Requerimiento;
import servicio.AbstractAsyncTask;
import servicio.ServiceDetalleRequerimiento;
import servicio.ServiceRequerimiento;


public class ActDetalleRequerimiento extends ActRequerimiento implements AbstractAsyncTask.IComunicatorBackgroundTask {

    //Servicios
    private ServiceRequerimiento serviceRequerimiento;
    private ServiceDetalleRequerimiento serviceDetalleRequerimiento;

    //GUI
    private TableLayout tblAVRRepuestos;

    /**EVENTOS*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detalle_requerimiento);

        Integer idRequerimiento = Integer.valueOf(extraerDato("idRequerimiento"));

        setTitle(getResources().getString(R.string.title_activity_act_detalle_requerimiento)+idRequerimiento);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();

        Map<String, Object> params = new HashMap<>();
        params.put("idRequerimiento", idRequerimiento);
        serviceRequerimiento = new ServiceRequerimiento(this, false);
        serviceRequerimiento.execute(3, 1, params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_detalle_requerimiento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAVRCerrar: finish(); break;
            default: break;
        }
    }

    /**INTERFACE*/
    //1. IComunicatorBackgroundTask
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
        onViewProcesar((Integer) result.get("idComponent"), result);
    }

    @Override
    public void canceledOnExecute(Integer id, Exception e) {

    }

    @Override
    public void showFragment(Fragmento fragmento) {

    }

    @Override
    public boolean isConnectNetwork(){
        return super.isConnectNetwork();
    }

    /**METODOS OVERRIDE*/
    @Override
    public void setListeners() {
        this.tblAVRRepuestos = (TableLayout) findViewById(R.id.tblAVRRepuestos);
        this.boton = (Button) findViewById(R.id.btnAVRCerrar);
        this.boton.setOnClickListener(this);
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
            case 1: cargarRequerimiento((Requerimiento) result.get("requerimiento")); break;
            case 2: cargarDetallesRequerimiento((List<DetalleRequerimiento>) result.get("detallesRequerimientos"));
            default: break;
        }
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void cargarRequerimiento(Requerimiento requerimiento) {
        if(requerimiento!=null){
            Cliente cliente = requerimiento.getCliente();
            llenarText(R.id.lblAVRCedula, cliente.cedulaCompleta());
            llenarText(R.id.lblAVRNombre, cliente.getNombre());
            llenarText(R.id.lblAVRTelefono, cliente.getTelefono());
            llenarText(R.id.lblAVRCorreo, cliente.getCorreo());
            llenarText(R.id.lblAVREstado, cliente.getCiudad().getEstado().getNombre());
            llenarText(R.id.lblAVRCiudad, cliente.getCiudad().getNombre());

            llenarText(R.id.lblAVRMarca, requerimiento.getMarcaVehiculo().getNombre());
            llenarText(R.id.lblAVRModelo, requerimiento.getModeloV());
            llenarText(R.id.lblAVRAnno, String.valueOf(requerimiento.getAnnoV()));

            String serialCarroceria = requerimiento.getSerialCarroceriaV();
            llenarText(R.id.lblAVRSerial, (serialCarroceria!=null) ? serialCarroceria : "No Especificado");

            final Boolean tipoRepuestoRequerimiento = requerimiento.getTipoRepuesto();
            if(tipoRepuestoRequerimiento!=null) {
                IConexionDAO.ObjetosCombo tipoRespuesto = findObject(new Predicate<IConexionDAO.ObjetosCombo>() {
                    @Override
                    public boolean apply(IConexionDAO.ObjetosCombo objetoCombo) {
                        return (objetoCombo.getId() == tipoRepuestoRequerimiento);
                    }
                }, FrgRequerimiento.llenarTiposRepuesto());

                llenarText(R.id.lblAVRTipoRepuesto, tipoRespuesto.toString());
            }
            else
                llenarText(R.id.lblAVRTipoRepuesto, "No Especificado");

            Map<String, Object> params = new HashMap<>();
            params.put("idRequerimiento", requerimiento.getIdRequerimiento());
            serviceDetalleRequerimiento = new ServiceDetalleRequerimiento(this, true);
            serviceDetalleRequerimiento.execute(2, 2, params);
        }
    }

    private void cargarDetallesRequerimiento(List<DetalleRequerimiento> detallesRequerimientos) {
        if(detallesRequerimientos!=null && detallesRequerimientos.size()>0){
            this.tabla = tblAVRRepuestos;
            this.encabezado = new String[]{"Descripcion *", "     ", "Cantidad *", "       ", "Estatus"};
            TableRow filaEncabezado=this.crearCeldaEncabezado(Color.BLACK, Gravity.LEFT);
            filaEncabezado.setBackgroundResource(R.drawable.rectangle_head_table);
            for(int i=0; i<detallesRequerimientos.size(); i++){
                DetalleRequerimiento detalle = detallesRequerimientos.get(i);

                TextView lblAVRDescripcion = new TextView(this);
                lblAVRDescripcion.setTextColor(Color.BLACK);
                lblAVRDescripcion.setText(detalle.getDescripcion());
                this.insertarObjeto(lblAVRDescripcion, i+1, Gravity.LEFT);

                TextView space1 = new TextView(this);
                space1.setText("  ");
                this.insertarObjeto(space1, i+1, Gravity.LEFT);

                TextView lblAVRCantidad = new TextView(this);
                lblAVRCantidad.setTextColor(Color.BLACK);
                lblAVRCantidad.setText(String.valueOf(detalle.getCantidad()));
                this.insertarObjeto(lblAVRCantidad, i+1, Gravity.LEFT);
            }
        }
    }

    private void llenarText(int id, String text){
        TextView lblView = (TextView) findViewById(id);
        lblView.setText(lblView.getText().toString()+" "+text);
    }
}
