package librerias.componentes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

@SuppressLint("ValidFragment")
public class YesNoDialog extends DialogFragment implements DialogInterface.OnClickListener{

	private boolean withYes;
	private boolean withNo;
	private boolean withNeutral;
	private String txtNeutral;
	private int layout;
	private String titulo;
	
	
	/**Constructor de la Clase*/
	public YesNoDialog( String titulo, boolean withYes, boolean withNo, boolean withNeutral,
			String txtNeutral, int layout) {
		super();
		this.withYes = withYes;
		this.withNo = withNo;
		this.withNeutral = withNeutral;
		this.txtNeutral = txtNeutral;
		this.layout = layout;
		this.titulo = titulo;
	}

	/**EVENTOS*/
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (this.layout!=-1){
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			// Inflate and set the layout for the dialog
			// Pass null as the parent view because its going in the dialog layout
			builder.setView(inflater.inflate(layout, null));
		}
	    
		builder.setMessage(titulo);
		if(this.withYes){
			builder.setPositiveButton("Aceptar", this);
		}
		if(this.withNo){
			builder.setNegativeButton("Cancelar", this);
		}
		if(this.withNeutral){
			builder.setNeutralButton(this.txtNeutral, this);
		}
        // Create the AlertDialog object and return it
        return builder.create();
	}
	
	//1. 
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		IYNComunicacionListener listener = (IYNComunicacionListener) this.getActivity();
		switch(which){
		case AlertDialog.BUTTON_POSITIVE:{
			listener.onClick("Si");
		};break;
		case AlertDialog.BUTTON_NEGATIVE:{
			listener.onClick("No");
		};break;
		case AlertDialog.BUTTON_NEUTRAL:{
			listener.onClick("Neutral");
		};break;
		}
	}

	
}
