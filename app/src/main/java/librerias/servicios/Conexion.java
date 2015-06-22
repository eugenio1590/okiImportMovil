package librerias.servicios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.util.Log;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

public final class Conexion {
	
	public static final String FORMATO_IMAGEN = "data:image/jpeg;base64,";
	
	/**Constructor de la Clase*/
	public Conexion() {
		// TODO Auto-generated constructor stub
	}
	
	/**Metodos Propios de la Clase*/
	//1. Permitira Conectarse a los servicios externos mediante solicitudes GET
	public static final String conexionGet(String url, String servicio, String parametros){
		//JSONObject json=null;
        String resultado="";
		HttpClient httpclient = new DefaultHttpClient();

		//String url = "http://192.168.221.128:81/servidor-restful/Despachador.php?servicio="+servicio+"&"+parametros;
        url = url+servicio+parametros;
		url = url.replaceAll(" ", "%20");
        Log.i("URL" + servicio, url);
		
		// Preparar un objeto request via method Get
		HttpGet httpget = new HttpGet(url); 
		//HttpPost httpget = new HttpPost(url);
		HttpResponse response;
		try {
			// Ejecutar el request
			response = httpclient.execute(httpget);
			// Obtener la entidad del response
			HttpEntity entity = response.getEntity();
			// Si el response no esta encerrado como una entity, no hay necesidad de preocuparse, liberar la conexion
			if (entity != null) {
				// el JSON Response es leido

				//InputStream instream = entity.getContent();   
				//String resultado = convertStreamToString(instream);
				 resultado = EntityUtils.toString(response.getEntity());
                Log.i("JSON" + servicio, resultado);
                Log.d("JSON" + servicio, resultado);
                Log.e("JSON" + servicio, resultado);
                //message.setText(resultado);
				
				// Un objeto JSONObject se crea
                return resultado;
				//json = new JSONObject(resultado);
				
				//forma de usar el objeto json
				//(String)json.get("exito")

				// Se cierra la entrada del stream y se libera la conexion
				//instream.close();
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/*//2.1 Permitira Conectarse a los servicios externos mediante solicitudes POST
	public static final <T> T conexionPost(String url, String servicio, String parametros){
		JSONObject json=null;
		HttpClient httpclient = new DefaultHttpClient();

		//String url = "http://192.168.221.128:81/servidor-restful/Despachador.php";

        Log.i("URL" + servicio, parametros);

		// Preparar un objeto request via method Post
		HttpPost httppost = new HttpPost(url);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		HttpResponse response;
		try {

			pairs.add(new BasicNameValuePair("servicio", servicio));

			String[] partes = parametros.split("&");
			for(int i=0; i< partes.length; i++){
				String[] parametro = partes[i].split("=");
				pairs.add(new BasicNameValuePair(parametro[0], parametro[1]));
			}

			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Ejecutar el request
			response = httpclient.execute(httppost);
			// Obtener la entidad del response
			HttpEntity entity = response.getEntity();
			// Si el response no esta encerrado como una entity, no hay necesidad de preocuparse, liberar la conexion
			if (entity != null) {
				// el JSON Response es leido

				//InputStream instream = entity.getContent();
				//String resultado = convertStreamToString(instream);
				String resultado = EntityUtils.toString(response.getEntity());
				//message.setText(resultado);

				// Un objeto JSONObject se crea
				json = new JSONObject(resultado);

				//forma de usar el objeto json
				//(String)json.get("exito")

				// Se cierra la entrada del stream y se libera la conexion
				//instream.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return (T) json;
	}*/

    //2.2 Permitira Conectarse a los servicios externos mediante solicitudes POST
    public static final <T> T conexionPostNueva(String url, String servicio, String parametros){
        JSONObject json=null;
        HttpClient httpclient = new DefaultHttpClient();

        //String url = "http://192.168.221.128:81/servidor-restful/Despachador.php";

        Log.i("URL" + servicio, parametros);

        // Preparar un objeto request via method Post
        HttpPost httppost = new HttpPost(url+servicio);
        httppost.setHeader("content-type", "application/json");

        HttpResponse response;
        try {

            StringEntity entity_string = new StringEntity(parametros);
            httppost.setEntity(entity_string);

            // Ejecutar el request
            response = httpclient.execute(httppost);
            // Obtener la entidad del response
            HttpEntity entity = response.getEntity();
            // Si el response no esta encerrado como una entity, no hay necesidad de preocuparse, liberar la conexion
            if (entity != null) {
                // el JSON Response es leido

                //InputStream instream = entity.getContent();
                //String resultado = convertStreamToString(instream);
                String resultado = EntityUtils.toString(response.getEntity());
                //message.setText(resultado);

                // Un objeto JSONObject se crea
                json = new JSONObject(resultado);

                //forma de usar el objeto json
                //(String)json.get("exito")

                // Se cierra la entrada del stream y se libera la conexion
                //instream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return (T) json;
    }


    public static final <T> T conexionPutNueva(String url, String servicio, String parametros){
        JSONObject json=null;
        HttpClient httpclient = new DefaultHttpClient();

        //String url = "http://192.168.221.128:81/servidor-restful/Despachador.php";

        Log.i("URL" + servicio, parametros);

        // Preparar un objeto request via method Post
        HttpPut httpput = new HttpPut(url+servicio);
        httpput.setHeader("content-type", "application/json");

        HttpResponse response;
        try {

            StringEntity entity_string = new StringEntity(parametros);
            httpput.setEntity(entity_string);

            // Ejecutar el request
            response = httpclient.execute(httpput);
            // Obtener la entidad del response
            HttpEntity entity = response.getEntity();
            // Si el response no esta encerrado como una entity, no hay necesidad de preocuparse, liberar la conexion
            if (entity != null) {
                // el JSON Response es leido

                //InputStream instream = entity.getContent();
                //String resultado = convertStreamToString(instream);
                String resultado = EntityUtils.toString(response.getEntity());
                //message.setText(resultado);

                // Un objeto JSONObject se crea
                json = new JSONObject(resultado);

                //forma de usar el objeto json
                //(String)json.get("exito")

                // Se cierra la entrada del stream y se libera la conexion
                //instream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return (T) json;
    }


    //3. Decodificara la imagen pasada por parametro y la agregara al imagenview
	public static final void decodificarImagen(ImageView imagenView, String jsonImagen){
		try {
			byte[] bytes = Base64.decode(jsonImagen, Base64.DECODE);
			Bitmap imagen = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			if(imagen!=null){
				imagenView.setImageBitmap(imagen);
				//guardarImagen(context, nombre, imagen);
                Log.i("Imagen Decodificada:", "Posible");
			}
			else
                Log.i("Imagen Decodificada:", "Imposible");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//4. Codificara la imagen en formato jpeg
	public static final String codificarImagen(ImageView imagenView){
		Drawable dra= imagenView.getDrawable();
		
		if(dra == null){
			dra = imagenView.getBackground();
		}
		
		Bitmap bmSrc1 = ((BitmapDrawable)dra).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		bmSrc1.compress(Bitmap.CompressFormat.JPEG, 90, stream); 
		byte [] byte_arr = stream.toByteArray();
		String image_str = Base64.encodeBytes(byte_arr);
		return image_str;
	}
	
	//5. Permitira guardar la imagen que se le indique por parametro
	public static String guardarImagen (Context context, String nombre, Bitmap imagen, boolean withAplicacion){
	    ContextWrapper cw = new ContextWrapper(context);
	    
	    File dirImages = null;
	    
	    if(withAplicacion)
	    	//Para storage en el directorio de la aplicacion
	    	dirImages = cw.getDir("imagenes", Context.MODE_PRIVATE);
	    else
	    	//Para storage en el sd card
	    	dirImages = new File(Environment.getExternalStoragePublicDirectory(
		            Environment.DIRECTORY_PICTURES), "imagenes");
	    
	    if(!dirImages.exists())
	    	dirImages.mkdirs();
	    
	    File myPath = new File(dirImages, nombre + ".png");
	     
	    FileOutputStream fos = null;
	    try{
	        fos = new FileOutputStream(myPath);
	        imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
	        fos.flush();
	    }catch (FileNotFoundException ex){
	        ex.printStackTrace();
	    }catch (IOException ex){
	        ex.printStackTrace();
	    }
	    return myPath.getAbsolutePath();
	}
}
