package librerias.componentes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class JNota extends LinearLayout{
	public static final String ESPACIO = "&nbsp;";
	
	private Encabezado encabezado;
	private TextView descripcion;
	private Button ver_mas;
	private BotonImagen ver_mas_imagen;
	private int fondo;
	private LinearLayout fondo_boton;
	
	public JNota(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		if(fondo!=0)
			this.setBackgroundResource(fondo);
		else
			this.setBackgroundResource(android.R.drawable.alert_light_frame);
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER);
		
		this.encabezado = new Encabezado(context);
		this.addView(encabezado);
		
		this.descripcion = new TextView(context);
		this.descripcion.setText("Descripcion");
		this.descripcion.setTextAppearance(context, android.R.attr.textAppearanceMedium);
		this.addView(descripcion);
		
		this.ver_mas = new Button(context);
		this.ver_mas.setText("Ver Mas");
		this.ver_mas.setBottom(android.R.attr.buttonStyleSmall);
		this.ver_mas.setGravity(Gravity.CENTER);
		this.ver_mas.setTextSize(12);
		this.addView(ver_mas);
	}

	public JNota(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		if(fondo!=0)
			this.setBackgroundResource(fondo);
		else
			this.setBackgroundResource(android.R.drawable.alert_light_frame);
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER);
		
		this.encabezado = new Encabezado(context);
		this.addView(encabezado);
		
		this.descripcion = new TextView(context);
		this.descripcion.setText("Descripcion");
		this.descripcion.setTextAppearance(context, android.R.attr.textAppearanceMedium);
		this.addView(descripcion);
		
		this.ver_mas = new Button(context);
		this.ver_mas.setText("Ver Mas");
		this.ver_mas.setBottom(android.R.attr.buttonStyleSmall);
		this.ver_mas.setGravity(Gravity.CENTER);
		this.addView(ver_mas);
	}
	
	public JNota(Context context, boolean withBtnImagen, int imagen, int height) {
		super(context);
		
		if(fondo!=0)
			this.setBackgroundResource(fondo);
		else
			this.setBackgroundResource(android.R.drawable.alert_light_frame);
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER);
		
		this.encabezado = new Encabezado(context);
		
		this.addView(encabezado);
		
		this.descripcion = new TextView(context);
		this.descripcion.setText("Descripcion");
		this.descripcion.setTextAppearance(context, android.R.attr.textAppearanceMedium);
		
		this.addView(descripcion);
		
		if(withBtnImagen){
			this.fondo_boton=new LinearLayout(context);
			this.ver_mas_imagen=new BotonImagen(context, false);
			this.ver_mas_imagen.setImagen(imagen, 0, height);
			this.fondo_boton.addView(this.ver_mas_imagen);
			this.fondo_boton.setGravity(Gravity.CENTER);
			this.fondo_boton.setOrientation(VERTICAL);
			
			this.addView(fondo_boton);
		}else{
			this.ver_mas = new Button(context);
			this.ver_mas.setText("Ver Mas");
			this.ver_mas.setBottom(android.R.attr.buttonStyleSmall);
			this.addView(ver_mas);
		}
		this.fondo_boton.setWeightSum(1);
	}
		
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		LayoutParams params;
		
		params=(LayoutParams) this.getLayoutParams();
		params.setMargins(0, 5, 0, 5);
		this.setPadding(0, 0, 0, 0);
		
		params = (LayoutParams) this.encabezado.getLayoutParams();
		this.encabezado.setPadding(20, 20, 20, 20);
		
		params = (LayoutParams) this.descripcion.getLayoutParams();
		params.setMargins(45, 16, 45, 16);
		
		if(this.ver_mas!=null){
			params = (LayoutParams) this.ver_mas.getLayoutParams();
			params.setMargins(65, 0, 65, 0);
		}
		else{
			params=(LayoutParams) this.ver_mas_imagen.getLayoutParams();
			params.width=LayoutParams.WRAP_CONTENT;
			params.height=LayoutParams.WRAP_CONTENT;
		}
	}

	/**Metodos Propios de la Clase*/
	public void setListener(int id, OnClickListener listener){
		if(this.ver_mas!=null){
			this.ver_mas.setId(id);
			this.ver_mas.setOnClickListener(listener);
		}
		else{
			this.ver_mas_imagen.setListener(id, listener);
		}
	}
	
	public void configuracionBoton(boolean withAbajo, int gravity){
		if(this.ver_mas_imagen!=null){
			LayoutParams params;
			params=(LayoutParams) this.ver_mas_imagen.getLayoutParams();
			params.gravity=gravity;
			
			if(withAbajo){
				Point p = BotonImagen.calcularEsquinaAbajo(this.ver_mas_imagen);
				this.ver_mas_imagen.setX(p.x-5);
				this.ver_mas_imagen.setY(p.y-5);
			}
		}
	}
	
	/**SETTERS*/
	public void setDescripcion(String texto){
		this.descripcion.setText(Html.fromHtml(texto), BufferType.SPANNABLE);
	}
	
	public void setAutor(String texto){
		this.encabezado.autor.setText(Html.fromHtml(texto), BufferType.SPANNABLE);
	}
	
	public void setFecha(String texto){
		this.encabezado.fecha.setText(Html.fromHtml(texto), BufferType.SPANNABLE);
	}
	
	public void setTextoBoton(String texto){
		if(this.ver_mas!=null)
			this.ver_mas.setText(texto);
		else
			this.ver_mas_imagen.setText(texto);
	}
	
	public void setFondo(int fondo){
		this.fondo = fondo;
		if(fondo!=0)
			this.setBackgroundResource(fondo);
		else
			this.setBackgroundResource(android.R.drawable.alert_light_frame);
	}
	
	/**Encabezado de la Nota*/
	private class Encabezado extends LinearLayout {
		
		private TextView autor;
		private TextView fecha;

		public Encabezado(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			
			this.setOrientation(HORIZONTAL);
			
			this.autor=new TextView(context); //Label
			this.autor.setText(Html.fromHtml("<b>Autor:</b>"+ESPACIO), BufferType.SPANNABLE);
			this.addView(autor);
			
			this.autor = new TextView(context); //TxtAutor
			this.autor.setMaxWidth(100);
			this.autor.setText(Html.fromHtml("<i>No Asigando</i>"), BufferType.SPANNABLE);
			this.addView(autor);
			
			this.fecha = new TextView(context);
			this.fecha.setText(Html.fromHtml("<b>Fecha:</b>"+ESPACIO), BufferType.SPANNABLE); //Label
			this.addView(fecha);
			
			this.fecha = new TextView(context); 
			this.fecha.setText(Html.fromHtml("<i>"+Calendario.getFechaHoyFormat()+"</i>"), BufferType.SPANNABLE); //TxtFecha
			this.addView(fecha);
		}
		
		public Encabezado(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
			
			this.setOrientation(HORIZONTAL);
			
			this.autor=new TextView(context); //Label
			this.autor.setText(Html.fromHtml("<b>Autor:</b>"+ESPACIO), BufferType.SPANNABLE);
			this.addView(autor);
			
			this.autor = new TextView(context); //TxtAutor
			this.autor.setMaxWidth(100);
			this.autor.setText(Html.fromHtml("<i>No Asigando</i>"), BufferType.SPANNABLE);
			this.addView(autor);
			
			this.fecha = new TextView(context);
			this.fecha.setText(Html.fromHtml("<b>Fecha:</b>"+ESPACIO), BufferType.SPANNABLE); //Label
			this.addView(fecha);
			
			this.fecha = new TextView(context); 
			this.fecha.setText(Html.fromHtml("<i>"+Calendario.getFechaHoyFormat()+"</i>"), BufferType.SPANNABLE); //TxtFecha
			this.addView(fecha);
		}
	}

}
