package app.okiimport.com.okiimport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Map;

import librerias.ActivityGeneric;

public abstract class ActRequerimiento extends ActivityGeneric {

    protected Menu menu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**METODOS ABSTRACTOS DE LA CLASE*/
    public abstract void onViewProcesar(Integer idView, Map<String, Object> result);

    /**METODOS PROPIOS DE LA CLASE*/
    protected boolean isConnectNetwork(){
        //Recogemos el servicio ConnectivityManager
        //el cual se encarga de todas las conexiones del terminal
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Recogemos el estado del 3G
        //como vemos se recoge con el parámetro 0
        NetworkInfo.State internet_movil = conMan.getNetworkInfo(0).getState();
        //Recogemos el estado del wifi
        //En este caso se recoge con el parámetro 1
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        //Miramos si el internet 3G está conectado o conectandose...
        if (internet_movil == NetworkInfo.State.CONNECTED || internet_movil == NetworkInfo.State.CONNECTING) {
            //El movil está conectado por 3G
            return true;

        }
        //Si no esta por 3G comprovamos si está conectado o conectandose al wifi...
        else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            //El movil está conectado por WIFI
            return true;
        }
        return false;
    }
}
