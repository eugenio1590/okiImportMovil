package librerias;

import android.content.Context;

interface IFuncionesGenerales extends IFuncionesFormularios{
	/**Interface requerida para la implementacion 
	 * futura en las clases derivadas de Activity
	 * */
	void setListeners();
	void mostrarMensaje(String mensaje);
	String extraerDato(String campo);
	void redireccionar(Context context, Class<?> clase, boolean withFinish);
}
