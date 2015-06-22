package librerias.componentes;

import android.R;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JCalendarAndroid extends LinearLayout implements OnClickListener, OnDateSetListener {
	
	private TextView fecha;
	private ImageButton boton;

	public JCalendarAndroid(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		fecha = new TextView(context);
		boton = new ImageButton(context);
		
		fecha.setText(Calendario.getFechaHoyFormat());
		
		boton.setBackgroundResource(R.drawable.ic_menu_my_calendar);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		this.setOrientation(HORIZONTAL);
		
		this.addView(fecha);
		this.addView(boton);
		
		boton.setOnClickListener(this);
	}

	public JCalendarAndroid(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		fecha = new TextView(context);
		boton = new ImageButton(context);
		
		fecha.setText(Calendario.getFechaHoyFormat());
		
		boton.setBackgroundResource(R.drawable.ic_menu_my_calendar);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		this.setOrientation(HORIZONTAL);
		
		this.addView(fecha);
		this.addView(boton);
		
		boton.setOnClickListener(this);
	}

	public JCalendarAndroid(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		fecha = new TextView(context);
		boton = new ImageButton(context);
		
		fecha.setText(Calendario.getFechaHoyFormat());
		
		boton.setBackgroundResource(R.drawable.ic_menu_my_calendar);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		this.setOrientation(HORIZONTAL);
		
		this.addView(fecha);
		this.addView(boton);
		
		boton.setOnClickListener(this);
	}

	//1. Evento Click cobre algun elemento del formularios
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Calendario calendario = new Calendario(this.getContext(), this);
		calendario.show();
	}

	//2. Evento Click cuando se ha seleccionado alguna fecha del DatePicker
	@Override
	public void onDateSet(DatePicker picker, int year, int month, int day) {
		// TODO Auto-generated method stub
		String fecha = String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
		this.fecha.setText(fecha);
	}
	
	/**Getter*/
	public String getFecha() {
		return fecha.getText().toString();
	}

	/**Metodos Propios de la Clase*/
	public boolean fechaVacia(){
		return fecha.getText().toString().trim().equals("");
	}
	
	public String fechaSQL(){
		return Calendario.getFechaFormatoSQL(this.getFecha());
	}
}
