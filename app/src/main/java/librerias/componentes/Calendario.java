package librerias.componentes;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import conexion.IConexionDAO.ObjetosCombo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;

public class Calendario extends DatePickerDialog {
	
	private static Calendar fecha_hoy = new GregorianCalendar(); 

	/**Constructor de la Clase*/
	//Sin tema
	public Calendario(Context context, OnDateSetListener callBack) {
		super(context, callBack, fecha_hoy.get(Calendar.YEAR), 
				fecha_hoy.get(Calendar.MONTH), fecha_hoy.get(Calendar.DAY_OF_MONTH));
		// TODO Auto-generated constructor stub
	}

	/**Constructor de la Clase*/
	//Con Tema
	public Calendario(Context context, int theme, OnDateSetListener callBack) {
		super(context, theme, callBack, fecha_hoy.get(Calendar.YEAR), 
				fecha_hoy.get(Calendar.MONTH), fecha_hoy.get(Calendar.DAY_OF_MONTH));
		// TODO Auto-generated constructor stub
	}
	
	/**GENERAL*/
	//1. Obtendra el Año de la Fecha Actual
	public static int getAnno(){
		return fecha_hoy.get(Calendar.YEAR);
	}
	
	//2. Obtendra el Mes de la Fecha Actual
	public static int getMes(){
		return fecha_hoy.get(Calendar.MONTH);
	}
	
	//3. Obtendra el Dia de la Fecha Actual
	public static int getDia(){
		return fecha_hoy.get(Calendar.DAY_OF_MONTH);
	}
	
	//4. Obtendra la Fecha de Hoy Completa en Formato dd/mm/YY
	public static String getFechaHoyFormat(){
		return String.valueOf(getDia())+"/"+String.valueOf(getMes()+1)+"/"+String.valueOf(getAnno());
	}
	
	
	//5. Obtendra la fecha pasada por parametro en Formato dd/mm/YY
	@SuppressWarnings("deprecation")
	public static String getFechaFormat(Date fecha){
		return String.valueOf(fecha.getDay())+"/"+fecha.getMonth()+1+"/"+fecha.getYear();
	}
	
	//6. Obtendra la fecha en formato sql
	public static String getFechaFormatoSQL(String fecha){
		String fecha_;
		String[] partes;
		if(fecha.equalsIgnoreCase("")){
			fecha_ = getFechaHoyFormat();
		}
		else{
			fecha_ = fecha;
		}
		
		partes = fecha_.split("/", 3);
		fecha_ = "";
		for(int i=partes.length-1; i>=0; i--){
			if(i!=0){
				fecha_+=partes[i]+"-";
			}
			else{
				fecha_+=partes[i];
			}
		}
		return fecha_;
	}
	
	//7. Permitira Sumar dias a la fecha especificada por parametro
	@SuppressLint("SimpleDateFormat")
	public static String sumarFecha(String fecha, int constante, int valor, int constante_retorno, boolean whithSQL){
		Date fecha_creada=null;
		try {
			fecha_creada=(DateFormat.getDateInstance()).parse(fecha);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha_creada); // Configuramos la fecha que se recibe
			calendar.add(constante, valor);  // numero de días a añadir, o restar en caso de días<0
			Date fecha_ = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
			if(whithSQL){
				return getFechaFormatoSQL(getFechaFormat(fecha_));
			}
			switch(constante_retorno){
			case Calendar.DAY_OF_MONTH: return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			case Calendar.MONTH: return String.valueOf(calendar.get(Calendar.MONTH)+1);
			case Calendar.YEAR: return String.valueOf(calendar.get(Calendar.YEAR));
			default: return getFechaFormat(fecha_);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fecha;
	}
	
	public static String getPrimerDiaDelMes(boolean whithSQL, int mes) {
        String fecha = "1/"+String.valueOf(mes+1)+"/"+String.valueOf(getAnno());
        if(whithSQL)
        	return getFechaFormatoSQL(fecha);
        else
        	return fecha;
    }

	public static String getUltimoDiaDelMes(boolean whithSQL, int mes) {
		Calendar c = new GregorianCalendar(getAnno(), mes, getDia());
		int ultimo_dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String fecha = String.valueOf(ultimo_dia)+"/"+String.valueOf(mes+1)+"/"+String.valueOf(getAnno());
		if(whithSQL)
			return getFechaFormatoSQL(fecha);
		else
			return fecha;
	}
	
	public static Vector<ObjetosCombo> getMeses(){
		Vector<ObjetosCombo> meses = new Vector<ObjetosCombo>();
		meses.add(new ObjetosCombo("-1", "Seleccionar..."));
		meses.add(new ObjetosCombo("1", "Enero"));
		meses.add(new ObjetosCombo("2", "Febrero"));
		meses.add(new ObjetosCombo("3", "Marzo"));
		meses.add(new ObjetosCombo("4", "Abril"));
		meses.add(new ObjetosCombo("5", "Mayo"));
		meses.add(new ObjetosCombo("6", "Junio"));
		meses.add(new ObjetosCombo("7", "Julio"));
		meses.add(new ObjetosCombo("8", "Agosto"));
		meses.add(new ObjetosCombo("9", "Septiembre"));
		meses.add(new ObjetosCombo("10", "Octubre"));
		meses.add(new ObjetosCombo("11", "Noviembre"));
		meses.add(new ObjetosCombo("12", "Diciembre"));
		return meses;
	}
}
