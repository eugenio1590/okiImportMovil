package librerias.componentes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FrgSelected extends DialogFragment implements OnClickListener{
	
	private CharSequence[] items ;
	private String titulo;

	/**EVENTOS*/
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		try{
			builder.setTitle(titulo);
			builder.setItems(items, this);
		} catch(Exception e){}
		return builder.create();
	}

	//1. Evento Click cobre algun elemento del formularios
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		String obj = (String) this.items[which];
		IComunicacionListener actividad = (IComunicacionListener) this.getActivity();
		actividad.onClickSelected(obj, which);
	}

	/**GETTERS Y SETTERS*/
	public CharSequence[] getItems() {
		return items;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setItems(CharSequence[] items) {
		this.items = items;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}   
	
	/**Metodos Propios de la Clase*/
	public static CharSequence[] concatenarCharSequence(CharSequence[] ...a){
		//este metodo recibe de 0 a n arreglos enteros y los puedes recorrer con esto
		int length=0;
		for(CharSequence[] aConcatenar:a){
			length+=aConcatenar.length;
		}
		
		CharSequence[] concatenado=new CharSequence[length];
		int i=0;
		for(CharSequence[] aConcatenar:a){
			for(CharSequence valor:aConcatenar){
				concatenado[i]=valor;
				i++;
			}
		}
		
		return concatenado;
	}
	
}
