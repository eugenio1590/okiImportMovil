package librerias.componentes;

import librerias.ActivityGeneric;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class BotonImagen extends LinearLayout {
	
	private Button boton;
	private TextView titulo;
	
	private Display display;
	
	/**Constructor de la Clase*/
	public BotonImagen(Context context){
		super(context);
		
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		titulo = new TextView(context);
		
		boton.setGravity(Gravity.CENTER);
		titulo.setTextSize(12);
		titulo.setGravity(Gravity.CENTER);
		titulo.setText("BotonImagen1");
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		this.addView(boton);
		this.addView(titulo);
	}
	
	public BotonImagen(Context context, boolean withTitulo){
		super(context);
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		boton.setGravity(Gravity.CENTER);
		
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		this.addView(boton);
		
		if(withTitulo){
			titulo = new TextView(context);
			titulo.setTextSize(12);
			titulo.setGravity(Gravity.CENTER);
			titulo.setText("BotonImagen1");
			this.addView(titulo);
		}
	}
	
	public BotonImagen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		titulo = new TextView(context);
		
		boton.setGravity(Gravity.CENTER);
		titulo.setTextSize(12);
		titulo.setGravity(Gravity.CENTER);
		titulo.setText("BotonImagen1");
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		
		this.addView(boton);
		this.addView(titulo);
	}
	
	public BotonImagen(Context context, AttributeSet attrs) {
		super(context, attrs);
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		titulo = new TextView(context);
		
		boton.setGravity(Gravity.CENTER);
		titulo.setTextSize(12);
		titulo.setGravity(Gravity.CENTER);
		titulo.setText("BotonImagen1");
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		
		this.addView(boton);
		this.addView(titulo);
	}
	
	//Con Metricas
	public BotonImagen(Context context, DisplayMetrics met){
		super(context);
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		titulo = new TextView(context);
		
		boton.setGravity(Gravity.CENTER);
		titulo.setTextSize(12);
		titulo.setGravity(Gravity.CENTER);
		titulo.setText("BotonImagen1");
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		this.setPadding(ActivityGeneric.dimension(15, met), 0, ActivityGeneric.dimension(15, met), 0);
		
		this.addView(boton);
		this.addView(titulo);
	}

	//Sin metricas
	public BotonImagen(Context context, int imagen, String texto) {
		super(context);
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		
		boton = new Button(context);
		titulo = new TextView(context);
		boton.setBackgroundResource(imagen);
		titulo.setText(texto);
		
		boton.setGravity(Gravity.CENTER);
		titulo.setTextSize(12);
		titulo.setGravity(Gravity.CENTER);
		this.setOrientation(VERTICAL);
		this.setGravity(VERTICAL);
		
		this.addView(boton);
		this.addView(titulo);
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.getLayoutParams().width=display.getWidth();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		this.getLayoutParams().width=display.getWidth();
	}

	/**Metodos Estaticos*/
	public static Point calcularEsquinaAbajo(View v){
		Point point = new Point();
		if(v.getParent() instanceof View){
			point.x=((View)v.getParent()).getWidth()-v.getWidth()-1;
			point.y=((View)v.getParent()).getHeight()-v.getHeight()-1;
		}
		else{
			point.x=0;
			point.y=0;
		}
		return point;
		
	}
	
	/**Metodos Propios de la Clase*/
	//1. Asigna al boton un clicklistener pasado por parametro
	public void setListener(int layout, OnClickListener listener){
		boton.setId(layout);
		boton.setOnClickListener(listener);
	}

	//2. Permitira asignarles propios al boton creado (imagen, el t
	// ama�o de la imagen
		//y el texto de ayuda)
	public void setAtributos(int imagen, String texto, int width, int height){
		setImagen(imagen, width, height);
		titulo.setText(texto);
	}

	//3. Permitira cambiar el texto del boton
	public void setText(String texto){
		this.titulo.setText(texto);
	}
	
	//4. Permitira cambiar la imagen del boton
	public void setImagen(int imagen, int width, int height){
		boton.setBackgroundResource(imagen);
		boton.getLayoutParams().height=height;
        boton.getLayoutParams().width=width;
	}
	
	//5. Permitira cambiar el tamanno de la letra del boton
	public void cambiarTamannoLetra(int size){
		titulo.setTextSize(size);
	}

}
