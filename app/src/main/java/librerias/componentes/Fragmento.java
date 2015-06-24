package librerias.componentes;

import java.util.Vector;

import org.json.JSONObject;

import librerias.FragmentoActivity;
import librerias.IFuncionesFormularios;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Fragmento extends DialogFragment implements IFuncionesFormularios, OnClickListener {

	protected String[] encabezado;
	
	protected TextView label;
	protected EditText campo_editable;
	protected Button boton;
	protected Spinner combo;
	protected ImageView foto;
    protected Bundle bundle;
	protected TableLayout tabla;
	protected TableRow fila;
	
	protected JSONObject objetjson;
	
	protected IComunicacionListener listener;
	protected String titulo;
	protected int layout;
	
	protected WebView web;

	/**Metodos Override*/
	@Override
	public void limpiarGeneric(int id) {
		// TODO Auto-generated method stub
		campo_editable = (EditText) getComponente(id);
		campo_editable.setText("");
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stub
		Context contexto = this.getActivity().getApplicationContext();
		Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();
	}

	//5. Permitira Validar si un campo editable del formulario esta vacio
	@Override
	public boolean campoVacio(int id){
		campo_editable = (EditText) getComponente(id);
		if (campo_editable!=null){
			return campo_editable.getText().toString().trim().equals("");
		}
		return false;
	}
	
	/**Constructor de la Clase*/
	public Fragmento(String titulo, int layout) {
		// TODO Auto-generated constructor stub
		this.titulo=titulo;
		this.layout=layout;
	}
	
	/**EVENTOS*/
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(layout, container);
		getDialog().setTitle(titulo);
		setListener(view);
		listener = (IComunicacionListener) getActivity();
		return view;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view=getView();
        setListener(view);
        listener = (IComunicacionListener) getActivity();
    }

    @Override
	public void show(FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		super.show(manager, tag);
	}

	@Override
	public int show(FragmentTransaction transaction, String tag) {
		// TODO Auto-generated method stub
		return super.show(transaction, tag);
	}

	/**METODOS PROPIOS DE LA CLASE*/
	public View getComponente(int id){
		return ((FragmentoActivity) getActivity()).getComponente(id);
	}
	
	protected boolean comboSeleccionado(int id){
		return ((Spinner) this.getComponente(id)).getSelectedItemId() != 0;
	}
	
	//1. Creara el encabezado de las columnas de acuerdo a un color especifico
	protected void crearCeldaEncabezado(int color) {
		if(tabla!=null){
			for (int i = 0; i < encabezado.length; i++) {
				insertarCampoLabel(encabezado[i], 0); //El cero Significa la fila unica que se creara
				label.setTextColor(color);
			}
		}
	}
		
	//2. Insertara un campo TextView en la columna "index" de la tabla referenciada
	protected void insertarCampoLabel(String valor, int index) {
		if(tabla!=null){

			label = new TextView(this.getActivity());
			label.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			label.setGravity(Gravity.CENTER_HORIZONTAL);
			label.setTypeface(null, Typeface.BOLD);
			label.setText(valor);

			insertarObjeto(label, index, Gravity.LEFT);
		}
	}

	//3. Insertara un campo EditText en la columna "index" de la tabla referenciada
	private void insertarCampoEditable(String valor, int index, int tipo_acceso) {
		// TODO Auto-generated method stub
		if(tabla!=null){

			campo_editable = new EditText(this.getActivity());
			campo_editable.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			campo_editable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			campo_editable.setGravity(Gravity.CENTER_HORIZONTAL);
			campo_editable.setTypeface(null, Typeface.BOLD);
			campo_editable.setInputType(tipo_acceso);
			campo_editable.setText(valor);

			insertarObjeto(campo_editable, index, Gravity.LEFT);
		}
	}

	//4. Insertara un Button en la columna "index" de la tabla referenciada
	protected void insertarBoton(int index, String texto, OnClickListener listener){
		if(tabla!=null){
			boton = new Button(this.getActivity());
			boton.setId(index);
			boton.setText(texto);
			boton.setTextSize(12);
			boton.setOnClickListener(listener);
			insertarObjeto(boton, index, Gravity.CENTER);
		}
	}

	//5. Insertara un campo View en la columna "index" de la tabla referenciada
	protected void insertarObjeto(View objeto, int index, int gravity) {
		// TODO Auto-generated method stub
		if(tabla!=null){
			int nro_filas = tabla.getChildCount();

			if((index < nro_filas) && (index>=0)){
				fila = (TableRow) tabla.getChildAt(index);
			}else{
				fila = new TableRow(this.getActivity());
				fila.setGravity(gravity);
				fila.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tabla.addView(fila);
			}
			fila.addView(objeto);
		}
	}

	//6. Insertara una fila completa en la tabla referenciada mediante un arreglo de datos
	protected void  insertarFila(String[] valores, boolean editable, int index, int tipo_acceso){

		if(tabla!=null){
			int fila = 0;

			if(index < 0) //Insertara la Fila al final de la tabla
				fila = tabla.getChildCount();
			else
				fila = index;

			for(int i=0; i<valores.length; i++){
				if(!editable)
					insertarCampoLabel(valores[i], fila);
				else
					insertarCampoEditable(valores[i], fila, tipo_acceso);
			}
		}

	}

	//7. Extraera un valor de una celda especifica de la tabla referenciada
	protected String extraerValorCelda(int fila, int columna) {
		String valor="";
		if(tabla!=null){
			int nro_fila = tabla.getChildCount();
			if(fila < nro_fila){
				this.fila = (TableRow) tabla.getChildAt(fila);
				if(columna < this.fila.getChildCount() && columna>0){
					View objeto = ((View) this.fila.getChildAt(columna));

					if(objeto instanceof TextView){
						valor=((TextView)objeto).getText().toString();
					}else if(objeto instanceof EditText){
						valor = ((EditText)objeto).getText().toString();
					}
				}
			}
		}
		return valor;
	}
	
	//8. Borrar todos los elementos de la tabla creada
	protected void borrarTabla(){
		if(this.tabla!=null)
			this.tabla.removeAllViews();
	}



	/**INTERFAZ PARA LA COMUNICACION CON EL ACTIVITY DEL FRAGMENTO*/
	protected IComunicacionListener getListener(){
		return (IComunicacionListener) getActivity();
	}

	//Metodos Requeridos
	protected abstract void setListener(View view);
	protected abstract void cargarCombo(int id, View view);
	
	//5. Insertara algun dato para pasarlo a otro Activity
		@Override
		public void redireccionarValores(Context context ,Class<?> clase, Vector<String[]> valores, boolean withFinish){
			
		}
}
