package librerias;

import java.util.Vector;

import librerias.componentes.BotonImagen;
import librerias.componentes.IComunicacionListener;

import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public abstract class FragmentoActivity extends FragmentActivity implements IFuncionesGenerales, IComunicacionListener, OnClickListener {

	protected Spinner combo;
	protected String[] encabezado;
	
	protected LinearLayout linear_layout;

	protected TableLayout tabla;
	protected TableRow fila;
	
	protected EditText campo_editable;
	protected TextView label;
	
	protected Button boton;
	protected ImageButton boton2;
	protected BotonImagen boton3;
	
	protected ImageView imagen;
	protected WebView web;
	
	protected CalendarView calendario;
	
	protected Bundle bundle;
	protected Intent intent;
	protected Context contexto;
	
	protected JSONObject objetjson;
	
	protected DialogFragment fragmento;
	
	protected Display display;

	/**Constructor de la Clase*/
	public FragmentoActivity() {
		// TODO Auto-generated constructor stub
	}
	
	/**EVENTOS*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		super.onCreate(savedInstanceState);
		this.contexto=this.getApplicationContext();
		this.display = getWindowManager().getDefaultDisplay();
	}

	/**METODOS PROPIOS DE LA CLASE*/	
		//Seccion Fragmentos
	//1. Permitira mostrar el fragmento si esta disponible
	protected void showFragmento(){
		if(fragmento != null){
			FragmentManager fm = getSupportFragmentManager();
			fragmento.show(fm, "fragment");
		}
	}
	
	//2. Permitira Cerrar el fragmento si esta disponible
	protected void closeFragmento(){
		if(fragmento != null){
			fragmento.dismiss();
		}
	}
	
	//3. Permitira buscar un componente en el view del fragmento si esta disponible
	public View getComponente(int id){
		if(fragmento != null)
			return fragmento.getView().findViewById(id);
		else return null;
	}
	
	//4. Permitira obtener el context del fragmento actual si esta disponible
	public Context getContexto(){
		if(fragmento != null){
			return fragmento.getActivity().getApplicationContext();
		}else return null;
	}
	
	/**ACTIVITY*/
	//1. Permitira Mostrar un mensaje en la pantalla
	@Override
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stub
		Toast.makeText(this.contexto, mensaje, Toast.LENGTH_LONG).show();
	}

	//2. Extraera algun dato pasado por otro activity
	@Override
	public String extraerDato(String campo) {
		// TODO Auto-generated method stub
		bundle=getIntent().getExtras();
		return bundle.getString(campo);
	}
	
	//3. Limpiara los Campos Editables de los Formularios
	@Override
	public void limpiarGeneric(int id) {
		// TODO Auto-generated method stub
		this.campo_editable=(EditText) findViewById(id);
		this.campo_editable.setText("");
	}

	//4. Redireccionara a otro activity especificado por parametro
	@Override
	public void redireccionar(Context context, Class<?> clase, boolean withFinish) {
		// TODO Auto-generated method stub
		intent = new Intent(context , clase);
		startActivityForResult(intent, 0);
		if(withFinish)
			this.finish();
	}
	
	//5. Insertara algun dato para pasarlo a otro Activity
		@Override
		public void redireccionarValores(Context context ,Class<?> clase, Vector<String[]> valores, boolean withFinish){
			intent = new Intent(context , clase);
			for(int i=0; i<valores.size(); i++){
				String[] valor = valores.get(i);
				intent.putExtra(valor[0], valor[1]);
			}
			startActivityForResult(intent, 0);
			if(withFinish)
				this.finish();
		}
	
	//5. Permitira Validar si un campo editable del formulario esta vacio
	@Override
	public boolean campoVacio(int id){
		campo_editable = (EditText) findViewById(id);
		if (campo_editable!=null){
			return campo_editable.getText().toString().trim().equals("");
		}
		return false;
	}

	//6. Llenara un edittexto con la informacion pasada como parametro
	public void llenarEditText(int id, String text)
	{
		this.campo_editable=(EditText) findViewById(id);
		this.campo_editable.setText(text);
	}

	//7. Obtendra el Valor de un edittexto con el id pasado comp parametro
	public String getInfoEditText(int id){
		this.campo_editable=(EditText) findViewById(id);
		return this.campo_editable.getText().toString();
	}

    //8. Desabilita el EditText
    public void DesabilitarEditText(int id, boolean bool)
    {
        this.campo_editable=(EditText) findViewById(id);
        this.campo_editable.setEnabled(bool);
    }

	/**TABLAS*/
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
			
			label = new TextView(this);
			label.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			label.setGravity(Gravity.CENTER_HORIZONTAL);
			label.setTypeface(null, Typeface.BOLD);
			label.setText(valor);
			
			insertarObjeto(label, index, Gravity.CENTER);
		}
	}
	
	//3. Insertara un campo EditText en la columna "index" de la tabla referenciada
	private void insertarCampoEditable(String valor, int index, int tipo_acceso) {
		// TODO Auto-generated method stub
		if(tabla!=null){
			
			campo_editable = new EditText(this);
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
			boton = new Button(this);
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
				fila = new TableRow(this);
				fila.setGravity(gravity);
				fila.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tabla.addView(fila);
			}
			fila.addView(objeto);
		}
	}
	
	//6. Insertara una fila completa en la tabla referenciada mediante un arreglo de datos
	protected void  insertarFila(String[] valores, boolean editable, int index, int tipo_acceso, int listener){
		
		if(tabla!=null){
			int fila = 0;
			
			if(index < 0) //Insertara la Fila al final de la tabla
				fila = tabla.getChildCount();
			else
				fila = index;
			
			for(int i=0; i<valores.length; i++){
				if(!editable){
					insertarCampoLabel(valores[i], fila);
					if(i==listener){
					if(this.label!=null){
						this.label.setId(fila);
						/*if(fila%2==0)
							this.label.setBackgroundColor(Color.BLUE);
						else
							this.label.setBackgroundColor(Color.GRAY);*/
						this.label.setClickable(true);
						this.label.setOnClickListener(this);
					}
					}
				}else
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

    //9. Mostrar Mensaje Toast
    protected void mostrarToast(String mensaje){

        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        View textView = toast.getView();
        LinearLayout icono = new LinearLayout(getApplicationContext());
        icono.setOrientation(LinearLayout.HORIZONTAL);
        ImageView view = new ImageView(getApplicationContext());
        view.setImageResource(android.R.drawable.ic_menu_info_details);
        icono.addView(view);
        icono.addView(textView);
        toast.setView(icono);
        toast.show();
    }
	
	/**IMAGENES*/
	protected void cargarImagen(int id, String html){
		this.web = (WebView) findViewById(id);
		if(this.web!=null){
			String mimeType = "text/html";
		    String encoding = "utf-8";
		    this.web.loadDataWithBaseURL("fake://not/needed", html, mimeType, encoding, "");
		}
	}

	//Metodos Requetidos
	protected abstract void cargarCombo(int id);
	
}
