package librerias.componentes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JButtonUploadImage extends Button implements OnClickListener{
	
	//public static int SELECT_IMAGE = 237488;
	private Activity padre;
	public static int SELECT_IMAGE = 0x0000ffff;
	
	private ImageView imagen;
	private TextView ruta;
	
	/**Constructor de la Clase*/
	public JButtonUploadImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setOnClickListener(this);
		padre = (Activity) context;
	}

	public JButtonUploadImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setOnClickListener(this);
		padre = (Activity) context;
	}

	public JButtonUploadImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setOnClickListener(this);
		padre = (Activity) context;
	}
	
	/**EVENTOS*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		intent.setType("image/*");
		padre.startActivityForResult(intent, SELECT_IMAGE);  
	}
	
	/**Getters y Setters*/
	public void setImagen(ImageView imagen){
		this.imagen=imagen;
	}
	
	public ImageView getImagen(){
		return this.imagen;
	}
	
	public void setRuta(TextView ruta){
		this.ruta = ruta;
	}
	
	public TextView getRuta(){
		return this.ruta;
	}
	
	public void setPadre(Activity padre){
		this.padre = padre;
	}
	
	/**Metodos Propios de la Clase*/
	public String rutaReal(){
		if(ruta != null)
			return this.ruta.getText().toString();
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
	
	public void procesarResultado(int requestCode, int resultCode, Intent data){
		if (requestCode == SELECT_IMAGE)
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				ruta.setText(getPath(selectedImage));
				imagen.setImageURI(selectedImage);
			} 
	}
	
	@SuppressWarnings("deprecation")
	private String getPath(Uri uri) {
		String[] projection = { android.provider.MediaStore.Images.Media.DATA };
		Cursor cursor = padre.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
