package librerias.componentes;

import android.view.View;


public interface IComunicacionListener {
	/**Interface requerida que permitira
	 * la comunicacion de los fragmentos con su respectivo activity
	 * */
	
	//1. Evento Click cobre algun elemento del formularios
	void onClickBoton(View v);
	
	//2. Evento Click sobre algun elemento de la lista emergente
	void onClickSelected(Object obj, int pos);
	
	//3. Evento para Cancelar el Fragmento
	void cancelar();

}
