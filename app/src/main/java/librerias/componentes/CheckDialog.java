package librerias.componentes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karam on 19/01/15.
 */
@SuppressLint("ValidFragment")
public class CheckDialog extends DialogFragment implements DialogInterface.OnClickListener, DialogInterface.OnMultiChoiceClickListener{


    private AlertDialog dialog;
    static IConmunicatorCheckDialog padre;
    final ArrayList seletedItems=new ArrayList();
    List<String> source;
    String titulo;

    public CheckDialog(IConmunicatorCheckDialog padre, String titulo, List<String> source){
        this.padre=padre;
        this.source=source;
        this.titulo=titulo;
    }

    /**EVENTOS*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //return builder.create();
        return this.mostrarCheckDialog();

    }

    @Override
    public void onClick(DialogInterface dialog, int item) {

        // TODO Auto-generated method stub
        switch(item){
            case 0:

                break;
        }


    }

    public Dialog mostrarCheckDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder((Context) padre);
        try{




            builder.setTitle(titulo);
            builder.setMultiChoiceItems(this.source.toArray(new String[]{}), null,this)

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            padre.clickOk(seletedItems);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            padre.clickCancel();
                        }
                    });

            dialog = builder.create();
            dialog.show();



    } catch(Exception e){}
    return dialog;
    }

    public IConmunicatorCheckDialog getPadre() {
        return padre;
    }

    public void setPadre(IConmunicatorCheckDialog padre) {
        this.padre = padre;
    }


    @Override
    public void onClick(DialogInterface dialog, int indexSelected,
                        boolean isChecked) {
        if (isChecked) {

            seletedItems.add(indexSelected);
        } else if (seletedItems.contains(indexSelected)) {

            seletedItems.remove(Integer.valueOf(indexSelected));
        }
    }

    public interface IConmunicatorCheckDialog{
        public void clickOk(ArrayList<Integer> seletedIndexs);
        public void clickCancel();
    }
}
