package librerias.componentes;

import android.R;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class JUploadImageFragment extends DialogFragment implements DialogInterface.OnClickListener {
	
	public static int SELECT_IMAGE = 0x0000ffff;
	public static int TAKE_PICTURE = 0x00000fff;

    private Activity padre;
	
	private ImageView imgPhoto;
	private TextView lblPhoto;
	
	/**Constructor de la Clase*/
	public JUploadImageFragment(ImageView imgPhoto, TextView lblPhoto){
		super();
		this.imgPhoto = imgPhoto;
		this.lblPhoto = lblPhoto;
	}
	
	/**EVENTOS*/
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		try{
			final CharSequence[] items = {"Seleccionar de la Galería", "Tomar una foto", "Cancelar"};

			builder.setTitle("Seleccionar una Foto");
			builder.setItems(items, this);
		} catch(Exception e){}
		return builder.create();
	}   

	@Override
	public void onClick(DialogInterface dialog, int item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(item){
		case 0:
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			intent.setType("image/*");
			padre.startActivityForResult(intent, SELECT_IMAGE);
			break;
			
		case 1:
			intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			padre.startActivityForResult(intent, TAKE_PICTURE);
			break;
		case 2:
			this.dismiss();
			break;
		}
	}
	
	/**Getters y Setters*/
	public void setImagen(ImageView imagen){
		this.imgPhoto=imagen;
	}
	
	public ImageView getImagen(){
		return this.imgPhoto;
	}
	
	public void setRuta(TextView ruta){
		this.lblPhoto = ruta;
	}
	
	public TextView getRuta(){
		return this.lblPhoto;
	}
	
	public void setPadre(Activity padre){
		this.padre = padre;
	}
	
	/**Metodos Propios de la Clase*/
	public String rutaReal(){
		if(lblPhoto != null)
			return this.lblPhoto.getText().toString();
		else
			return "";
	}
	
	public String nombreConExtencion(){
		String ruta  = this.rutaReal();
		String[] partes = ruta.split("/");
		return partes[partes.length-1];
	}
	
	public String extencion(){
		String ruta  = this.rutaReal();
		String extencion="No tiene";
		
		for(int i=0; i< ruta.length(); i++){
			if(ruta.charAt(i) == '.'){
				extencion = ruta.substring(i, ruta.length());
			}
		}
		
		return extencion;
	}
	
	public String nombreSinExtencion(){
		String ruta = this.nombreConExtencion();
		String nombre = "No Tiene";
		for(int i=0; i< ruta.length(); i++){
			if(ruta.charAt(i) == '.'){
				nombre = ruta.substring(0, i);
			}
		}
		return nombre;
	}
	
	@SuppressWarnings("deprecation")
	private String getPath(Uri uri) {
		String[] projection = { android.provider.MediaStore.Images.Media.DATA };
		Cursor cursor = padre.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	public void procesarResultado(int requestCode, int resultCode, Intent data){
		if (requestCode == SELECT_IMAGE)
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				//lblPhoto.setText(getPath(selectedImage));
				imgPhoto.setImageURI(selectedImage);
			} 
		if(requestCode == TAKE_PICTURE)
			if(resultCode == Activity.RESULT_OK){
				Uri selectedImage = data.getData();
				//lblPhoto.setText(getPath(selectedImage));
				//imgPhoto.setImageURI(selectedImage);
                imgPhoto.setImageBitmap((android.graphics.Bitmap) data.getExtras().get("data"));
			}
	}
	
}
