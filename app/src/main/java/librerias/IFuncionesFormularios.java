package librerias;

import java.util.Vector;

import android.content.Context;
import android.view.View;

public interface IFuncionesFormularios{
	/**Interface requerida para la implementacion 
	 * futura en las clases derivadas de Activity, Fragmento
	 * */
	//1. Metodos Abstractos Generales
	void limpiarGeneric(int id);
    <T> T getGeneric(int id, Class<T> tClass);
	boolean campoVacio(int id);
	void mostrarMensaje(String mensaje);
	
	//2. Metodos Abstractos para las Validaciones de los Formularios
	boolean validarFormulario();
	void limpiar();
	void redireccionarValores(Context context, Class<?> clase, Vector<String[]> valores, boolean withFinish);

}
