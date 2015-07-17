package app.okiimport.com.okiimport.fragmentos;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import app.okiimport.com.okiimport.R;
import app.okiimport.com.okiimport.fragmentos.configuracion.EFrgTitulos;
import conexion.IConexionDAO.ObjetosCombo;
import librerias.ActivityGeneric;
import librerias.componentes.Calendario;
import librerias.componentes.ViewValidator;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.DetalleRequerimiento;
import modelo.Estado;
import modelo.MarcaVehiculo;
import modelo.Requerimiento;
import servicio.AbstractAsyncTask.IComunicatorBackgroundTask;
import servicio.ServiceCiudad;
import servicio.ServiceCliente;
import servicio.ServiceDetalleRequerimiento;
import servicio.ServiceEstado;
import servicio.ServiceMarcaVehiculo;
import servicio.ServiceRequerimiento;


public class FrgRegistrarRequerimiento extends FrgRequerimiento implements OnItemSelectedListener, View.OnFocusChangeListener {

    public static final String TITULO = EFrgTitulos.FRG_REGISTRAR_REQUERIMIENTO.getValue();

    //Servicios
    private ServiceEstado serviceEstado;
    private ServiceCiudad serviceCiudad;
    private ServiceMarcaVehiculo serviceMarcaVehiculo;
    private ServiceRequerimiento serviceRequerimiento;

    //GUI
    private Spinner spnFRQTipoPersona;
    private Spinner spnFRQEstado;
    private Spinner spnFRQCiudad;
    private Spinner spnFRQMarca;

    private Button btnFRQAgregar;
    private Button btnFRQEliminar;
    private Button btnFRQLimpiar;
    private Button btnFRQEnviar;

    private EditText txtFRQAnno;
    private EditText txtFRQCedula;

    private TableLayout tblFRQRepuestos;

    //Validator
    private ViewValidator.TxtValidator txtValidatorFRQAnno;

    private Map<String, ViewValidator.TxtValidator> mapValidator;

    //Modelos
    private List<CheckBox> checkRemover;
    private List<Estado> estados;
    private List<Ciudad> ciudades;
    private List<MarcaVehiculo> marcasVehiculo;

    private Estado estado;
    private Ciudad ciudad;
    private MarcaVehiculo marcaVehiculo;
    private Cliente cliente;

    public FrgRegistrarRequerimiento() {
        super(TITULO, R.layout.fragment_frg_registrar_requerimiento);
        checkRemover = new ArrayList<CheckBox>();
        mapValidator = new LinkedHashMap<String, ViewValidator.TxtValidator>();
    }

    /**EVENTOS*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceEstado = new ServiceEstado((IComunicatorBackgroundTask) listener, false);
        serviceEstado.execute(1, R.id.spnFRQEstado);
        serviceMarcaVehiculo = new ServiceMarcaVehiculo((IComunicatorBackgroundTask) listener, true);
        serviceMarcaVehiculo.execute(1, R.id.spnFRQMarca);
    }

    @Override
    public void onClick(View v) {
        ActivityGeneric.imprimirConsola("Clase", v.getClass().getCanonicalName());
        if(v instanceof CheckBox){
            CheckBox checkRepuesto = (CheckBox) v;
            ActivityGeneric.imprimirConsola("CHECK",  "Id:"+checkRepuesto.getId());

            if(checkRepuesto.isChecked())
                checkRemover.add(checkRepuesto);
            else
                checkRemover.remove(checkRepuesto);
        }
        else if(v instanceof Button) {
            switch (v.getId()) {
                case R.id.btnFRQAgregar: {
                    int nroFila = tblFRQRepuestos.getChildCount();
                    if(nroFila<11)
                        agregarRepuesto(false, nroFila);
                }; break;

                case R.id.btnFRQEliminar: eliminarRepuestos(); break;

                case R.id.btnFRQLimpiar: limpiar(); break;

                case R.id.btnFRQEnviar: {
                    if(validarFormulario())
                        registrarCliente();
                }; break;
                default: break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ObjetosCombo item = (ObjetosCombo) ((Spinner) parent).getItemAtPosition(position);
        String idObjeto = String.valueOf(item.getId());
        Map<String, Object> params = null;
        switch (((Spinner) parent).getId()){
            case R.id.spnFRQEstado: {

                final int idEstado = Integer.parseInt(idObjeto);
                estado = ActivityGeneric.findObject(new Predicate<Estado>() {
                    @Override
                    public boolean apply(Estado estado) {
                        return (estado.getIdEstado()==idEstado);
                    }
                }, estados);
                params = new HashMap<String, Object>();
                params.put("idEstado", idObjeto);
                serviceCiudad = new ServiceCiudad((IComunicatorBackgroundTask) listener, true);
                serviceCiudad.execute(1, R.id.spnFRQCiudad, params);
            }; break;
            case R.id.spnFRQCiudad: {
                final int idCiudad = Integer.parseInt(idObjeto);
                ciudad = ActivityGeneric.findObject(new Predicate<Ciudad>() {
                    @Override
                    public boolean apply(Ciudad ciudad) {
                        return (ciudad.getIdCiudad()==idCiudad);
                    }
                }, ciudades);
            }; break;
            case R.id.spnFRQMarca: {
                final int idMarcaVehiculo = Integer.parseInt(idObjeto);
                marcaVehiculo = ActivityGeneric.findObject(new Predicate<MarcaVehiculo>() {
                    @Override
                    public boolean apply(MarcaVehiculo marcaVehiculo) {
                        return (marcaVehiculo.getIdMarcaVehiculo()==idMarcaVehiculo);
                    }
                }, marcasVehiculo);
            }; break;
            default: break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus && !this.txtFRQCedula.getText().toString().trim().toLowerCase().equalsIgnoreCase("")) {
            ObjetosCombo tipoPersona = getTipoPersona();
            String cedula = tipoPersona.toString() + this.txtFRQCedula.getText().toString();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cedula", cedula);
            ServiceCliente serviceCliente = new ServiceCliente((IComunicatorBackgroundTask) listener, true);
            serviceCliente.execute(2, R.id.txtFRQCedula, params);
        }
    }

    /**METODOS OVERRIDE*/
    @Override
    protected void setListener(View view) {
        spnFRQTipoPersona = (Spinner) view.findViewById(R.id.spnFRQTipoPersona);
        spnFRQEstado = (Spinner) view.findViewById(R.id.spnFRQEstado);
        spnFRQCiudad = (Spinner) view.findViewById(R.id.spnFRQCiudad);
        spnFRQMarca = (Spinner) view.findViewById(R.id.spnFRQMarca);

        btnFRQAgregar = (Button) view.findViewById(R.id.btnFRQAgregar);
        btnFRQEliminar = (Button) view.findViewById(R.id.btnFRQEliminar);
        btnFRQLimpiar = (Button) view.findViewById(R.id.btnFRQLimpiar);
        btnFRQEnviar = (Button) view.findViewById(R.id.btnFRQEnviar);

        txtFRQAnno = (EditText) view.findViewById(R.id.txtFRQAnno);
        txtFRQCedula = (EditText) view.findViewById(R.id.txtFRQCedula);

        tblFRQRepuestos = (TableLayout) view.findViewById(R.id.tblFRQRepuestos);

        cargarCombo(R.id.spnFRQTipoPersona, view);

        spnFRQEstado.setOnItemSelectedListener(this);
        spnFRQCiudad.setOnItemSelectedListener(this);
        spnFRQMarca.setOnItemSelectedListener(this);

        btnFRQAgregar.setOnClickListener(this);
        btnFRQEliminar.setOnClickListener(this);
        btnFRQLimpiar.setOnClickListener(this);
        btnFRQEnviar.setOnClickListener(this);


        txtFRQCedula.setOnFocusChangeListener(this);

        agregarRepuesto(true, 1);
    }

    @Override
    protected void setValidator(View view) {
        ViewValidator vvFRQAnno = (ViewValidator) view.findViewById(R.id.vvFRQAnno);

        vvFRQAnno.setImageResource(R.drawable.warning);
        vvFRQAnno.setImageBackgroundColor(Color.TRANSPARENT);

        txtFRQAnno.addTextChangedListener(txtValidatorFRQAnno=new ViewValidator.TxtValidator(txtFRQAnno, vvFRQAnno, R.drawable.edittext_error){

            @Override
            public boolean validateAfterChange(EditText editText, String text) {
                int annoActual = Calendario.getAnno();
                if(text.trim().equalsIgnoreCase("") ){
                    error = "Campo obligatorio";
                    return false;
                }
                else if(Integer.valueOf(text)>annoActual) {
                    error = "El año ingresado debe ser menor a "+annoActual;
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void cargarCombo(int id, View view) {
        switch (id){
            case R.id.spnFRQTipoPersona: ActivityGeneric.cargarCombo(R.id.spnFRQTipoPersona, view, llenarTiposPersona()); break;
            default: break;
        }
    }

    @Override
    public boolean validarFormulario() {
        if(!txtValidatorFRQAnno.validate())
            return false;

        if(!this.mapValidator.isEmpty()) {
            for(String key : this.mapValidator.keySet()){
                ViewValidator.TxtValidator txtValidator = this.mapValidator.get(key);
                if(txtValidator!=null && !txtValidator.validate())
                    return false;
            }
        }

        return true;
    }

    @Override
    public void limpiar() {
        limpiarGeneric(R.id.txtFRQCedula);
        limpiarGeneric(R.id.txtFRQNombre);
        limpiarGeneric(R.id.txtFRQCorreo);
        limpiarGeneric(R.id.txtFRQTelefono);
        limpiarGeneric(R.id.txtFRQModelo);
        limpiarGeneric(R.id.txtFRQAnno);
        limpiarGeneric(R.id.txtFRQSerial);

        spnFRQTipoPersona.setSelection(0);
        spnFRQEstado.setSelection(0);
        spnFRQMarca.setSelection(0);

        this.checkRemover.clear();
        this.mapValidator.clear();
        agregarRepuesto(true, 1);
    }

    @Override
    public void onViewProcesar(Integer idView, Map<String, Object> result){
        switch (idView){
            case R.id.spnFRQEstado: cargarListaEstado(result); break;
            case R.id.spnFRQCiudad: cargarListaCiudades(result); break;
            case R.id.spnFRQMarca: cargarListaMarcaVehiculos(result); break;
            case R.id.txtFRQCedula: cargarCliente(result); break;
            case R.id.btnFRQEnviar: {
                mostrarMensaje("Requerimiento Registrado");
                limpiar();
            }; break;
            case 1: registrarRequerimiento((Cliente) result.get("cliente")); break;
            case 2: registrarDetalleRequerimiento((Requerimiento) result.get("requerimiento")); break;
            default: break;
        }
    }

    /**METODOS PROPIOS DE LA CLASE*/
    private void cargarListaEstado(Map<String, Object> result){
        estados = (List<Estado>) result.get("estados");
        if(estados!=null) {
            Vector<ObjetosCombo> estadosCombo = new Vector<ObjetosCombo>();

            for (Estado estado : estados)
                estadosCombo.add(new ObjetosCombo(estado.getIdEstado(), estado.getNombre()));

            ActivityGeneric.cargarCombo(R.id.spnFRQEstado, getView(), estadosCombo);
        }
    }

    private void cargarListaCiudades(Map<String, Object> result){
        ciudades = (List<Ciudad>) result.get("ciudades");
        if(ciudades!=null) {
            Vector<ObjetosCombo> ciudadesCombo = new Vector<ObjetosCombo>();

            for (Ciudad ciudad : ciudades)
                ciudadesCombo.add(new ObjetosCombo(ciudad.getIdCiudad(), ciudad.getNombre()));

            ActivityGeneric.cargarCombo(R.id.spnFRQCiudad, getView(), ciudadesCombo);
        }
    }

    private void cargarListaMarcaVehiculos(Map<String, Object> result){
        marcasVehiculo = (List<MarcaVehiculo>) result.get("marcas");
        if(marcasVehiculo!=null) {
            Vector<ObjetosCombo> marcasVehiculosCombo = new Vector<ObjetosCombo>();

            for (MarcaVehiculo marca : marcasVehiculo)
                marcasVehiculosCombo.add(new ObjetosCombo(marca.getIdMarcaVehiculo(), marca.getNombre()));

            ActivityGeneric.cargarCombo(R.id.spnFRQMarca, getView(), marcasVehiculosCombo);
        }
    }

    private void cargarCliente(Map<String, Object> result) {
        if(result!=null && result.get("cliente")!=null){
            cliente = (Cliente) result.get("cliente");
            EditText txtFRQNombre = (EditText) getView().findViewById(R.id.txtFRQNombre);
            txtFRQNombre.setText(cliente.getNombre());
            EditText txtFRQCorreo = (EditText) getView().findViewById(R.id.txtFRQCorreo);
            txtFRQCorreo.setText(cliente.getCorreo());
            EditText txtFRQTelefono = (EditText) getView().findViewById(R.id.txtFRQTelefono);
            txtFRQTelefono.setText(cliente.getTelefono());
        }
    }

    private void agregarRepuesto(boolean withEncabezado, int nroFila){
        super.tabla = this.tblFRQRepuestos;

        if(withEncabezado) {
            this.borrarTabla();
            this.encabezado = new String[]{"", "  ", "Descripcion *", "     ", "Cantidad *"};
            TableRow filaEncabezado=this.crearCeldaEncabezado(Color.BLACK, Gravity.LEFT);
            filaEncabezado.setBackgroundResource(R.drawable.rectangle_head_table);
        }

        CheckBox chFRQRepuesto = new CheckBox(this.getActivity());
        chFRQRepuesto.setId(nroFila);
        chFRQRepuesto.setOnClickListener(this);
        this.insertarObjeto(chFRQRepuesto, nroFila, Gravity.LEFT);

        TextView space1 = new TextView(this.getActivity());
        space1.setText("  ");
        this.insertarObjeto(space1, nroFila, Gravity.LEFT);

        LinearLayout llFRQDescripcion = new LinearLayout(this.getActivity());
        llFRQDescripcion.setOrientation(LinearLayout.VERTICAL);
        llFRQDescripcion.setGravity(Gravity.CENTER);

        //Validator de la Descripcion
        ViewValidator vvFRQDescripcion = new ViewValidator(this.getActivity());
        vvFRQDescripcion.setLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vvFRQDescripcion.setImageResource(R.drawable.warning);
        vvFRQDescripcion.setImageBackgroundColor(Color.TRANSPARENT);
        vvFRQDescripcion.getTxtError().setTextSize(10f);
        //

        ViewValidator.TxtValidator txtValidatorFRQDescripcion;
        EditText txtFRQDescripcion = new EditText(this.getActivity());
        txtFRQDescripcion.setId(chFRQRepuesto.getId() + 10);
        txtFRQDescripcion.setTextColor(Color.BLACK);
        txtFRQDescripcion.setEms(6);
        txtFRQDescripcion.addTextChangedListener(txtValidatorFRQDescripcion=new ViewValidator.TxtValidator(txtFRQDescripcion, vvFRQDescripcion, R.drawable.edittext_error) {
            @Override
            public boolean validateAfterChange(EditText editText, String text) {
                if(text.trim().equalsIgnoreCase("")) {
                    error = "Campo obligatorio";
                    return false;
                }
                return true;
            }
        });

        llFRQDescripcion.addView(txtFRQDescripcion);
        llFRQDescripcion.addView(vvFRQDescripcion);
        this.insertarObjeto(llFRQDescripcion, nroFila, Gravity.LEFT);

        TextView space2 = new TextView(this.getActivity());
        space2.setText("     ");
        this.insertarObjeto(space2, nroFila, Gravity.LEFT);

        LinearLayout llFRQCantidad = new LinearLayout(this.getActivity());
        llFRQCantidad.setOrientation(LinearLayout.VERTICAL);
        llFRQCantidad.setGravity(Gravity.CENTER);

        // Validator de la Cantidad
        ViewValidator vvFRQCantidad = new ViewValidator(this.getActivity());
        vvFRQCantidad.setLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vvFRQCantidad.setImageResource(R.drawable.warning);
        vvFRQCantidad.setImageBackgroundColor(Color.TRANSPARENT);
        vvFRQCantidad.getTxtError().setTextSize(10f);
        //

        ViewValidator.TxtValidator nmbValidatorFRQCantidad;
        EditText nmbFRQCantidad = new EditText(this.getActivity());
        nmbFRQCantidad.setId(chFRQRepuesto.getId()+20);
        nmbFRQCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
        nmbFRQCantidad.setTextColor(Color.BLACK);
        nmbFRQCantidad.addTextChangedListener(nmbValidatorFRQCantidad=new ViewValidator.TxtValidator(nmbFRQCantidad, vvFRQCantidad, R.drawable.edittext_error) {
            @Override
            public boolean validateAfterChange(EditText editText, String text) {
                if(!text.trim().equalsIgnoreCase("")) {
                    try {
                        int cantidad = Integer.valueOf(text);
                        if (cantidad <= 0) {
                            error = "Cant. mayor que 0";
                            return false;
                        }
                    }
                    catch (NumberFormatException e){
                        error = "Cant. Excedida";
                        return false;
                    }
                }
                else{
                    error = "Campo obligatorio";
                    return false;
                }
                return true;
            }
        });

        this.mapValidator.put("txtValidatorFRQDescripcion"+nroFila, txtValidatorFRQDescripcion);
        this.mapValidator.put("nmbValidatorFRQCantidad"+nroFila, nmbValidatorFRQCantidad);

        llFRQCantidad.addView(nmbFRQCantidad);
        llFRQCantidad.addView(vvFRQCantidad);
        this.insertarObjeto(llFRQCantidad, nroFila, Gravity.LEFT);
    }

    private void eliminarRepuestos(){
        this.tabla = tblFRQRepuestos;
        Collections.sort(checkRemover, new Comparator<CheckBox>() {
            @Override
            public int compare(CheckBox checkBox1, CheckBox checkBox2) {
                return Integer.valueOf(checkBox2.getId()).compareTo(checkBox1.getId());
            }
        });
        for (int i = 0; i < checkRemover.size(); i++) {
            CheckBox checkBox = checkRemover.get(i);
            this.mapValidator.remove("txtValidatorFRQDescripcion" + checkBox.getId());
            this.mapValidator.remove("nmbValidatorFRQCantidad" + checkBox.getId());
            this.tabla.removeView((View) checkBox.getParent());
        }
        checkRemover.clear();
    }

    private ObjetosCombo getTipoPersona(){
        return (ObjetosCombo) spnFRQTipoPersona.getSelectedItem();
    }

    private void registrarCliente(){
        ObjetosCombo tipoPersona = getTipoPersona();
        if(cliente==null)
            cliente = new Cliente();
        cliente.setCedula(tipoPersona.toString()+getGeneric(R.id.txtFRQCedula, String.class));
        cliente.setNombre(getGeneric(R.id.txtFRQNombre, String.class));
        cliente.setCorreo(getGeneric(R.id.txtFRQCorreo, String.class));
        cliente.setTelefono(getGeneric(R.id.txtFRQTelefono, String.class));
        ciudad.setEstado(estado);
        cliente.setCiudad(ciudad);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cliente", cliente);
        ServiceCliente serviceCliente = new ServiceCliente((IComunicatorBackgroundTask) listener, false);
        serviceCliente.execute(1, 1, params);
    }

    private void registrarRequerimiento(Cliente cliente){
        if(cliente!=null) {
            Requerimiento requerimiento = new Requerimiento(cliente);
            requerimiento.setMarcaVehiculo(marcaVehiculo);
            requerimiento.setModeloV(getGeneric(R.id.txtFRQModelo, String.class));
            requerimiento.setAnnoV(Integer.valueOf(getGeneric(R.id.txtFRQAnno, String.class)));
            requerimiento.setSerialCarroceriaV(getGeneric(R.id.txtFRQSerial, String.class));

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("requerimiento", requerimiento);
            serviceRequerimiento = new ServiceRequerimiento((IComunicatorBackgroundTask) listener, false); //va false
            serviceRequerimiento.execute(1, 2, params);
        }
    }

    private void registrarDetalleRequerimiento(Requerimiento requerimiento){
        ServiceDetalleRequerimiento serviceDetalleRequerimiento = null;
        if(requerimiento!=null) {
            this.tabla = tblFRQRepuestos;
            ActivityGeneric.imprimirConsola("IdRequerimiento", ""+requerimiento.getIdRequerimiento());
            for (int i = 1; i < tabla.getChildCount(); i++) {
                Integer idComponent = null;
                EditText txtFRQDescripcion = (EditText) this.getComponente(i + 10);
                EditText nmbFRQCantidad = (EditText) this.getComponente(i + 20);

                DetalleRequerimiento detalleRequerimiento = new DetalleRequerimiento();
                detalleRequerimiento.setDescripcion(txtFRQDescripcion.getText().toString());
                detalleRequerimiento.setCantidad(Long.valueOf(nmbFRQCantidad.getText().toString()));
                detalleRequerimiento.setRequerimiento(requerimiento);

                if(i+1==tabla.getChildCount()) {
                    serviceDetalleRequerimiento = new ServiceDetalleRequerimiento((IComunicatorBackgroundTask) listener, true);
                    idComponent = R.id.btnFRQEnviar;
                }
                else {
                    serviceDetalleRequerimiento = new ServiceDetalleRequerimiento((IComunicatorBackgroundTask) listener, false);
                    idComponent = -1;
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("idRequerimiento", requerimiento.getIdRequerimiento());
                params.put("detalleRequerimiento", detalleRequerimiento);
                serviceDetalleRequerimiento.execute(1, idComponent, params);
            }

            //Falta llamar al servicio
        }
    }


}