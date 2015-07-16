package app.okiimport.com.okiimport.fragmentos.configuracion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import app.okiimport.com.okiimport.R;

import java.text.SimpleDateFormat;
import java.util.List;

import modelo.Requerimiento;

public class AdptRequerimiento extends ArrayAdapter<Requerimiento> {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AdptRequerimiento(Context context, List<Requerimiento> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // holder pattern
        AdptHolderRequerimiento holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adpt_requerimientos, parent, false);

            holder = new AdptHolderRequerimiento();
            holder.setLblAdpVQIdRequerimiento((TextView) convertView.findViewById(R.id.lblAdpVQIdRequerimiento));
            holder.setLblAdpVQFechaCreacion((TextView) convertView.findViewById(R.id.lblAdpVQFechaCreacion));
            holder.setLblAdpVQModeloV((TextView) convertView.findViewById(R.id.lblAdpVQModeloV));
            holder.setLblAdpVQSerialV((TextView) convertView.findViewById(R.id.lblAdpVQSerialV));

            convertView.setTag(holder);
        }
        else {
            holder = (AdptHolderRequerimiento) convertView.getTag();
        }


        Requerimiento requerimiento = getItem(position);
        if(requerimiento!=null) {
            holder.getLblAdpVQIdRequerimiento().setText("Nro. Requerimiento: "+String.valueOf(requerimiento.getIdRequerimiento()));
            holder.getLblAdpVQFechaCreacion().setText(dateFormat.format(requerimiento.getFechaCreacion()));
            holder.getLblAdpVQModeloV().setText("Modelo: "+requerimiento.getModeloV());
            holder.getLblAdpVQSerialV().setText("Serial: "+requerimiento.getSerialCarroceriaV());
        }


        return convertView;
    }

    private static class AdptHolderRequerimiento
    {

        private TextView lblAdpVQIdRequerimiento;

        private TextView lblAdpVQFechaCreacion;

        private TextView lblAdpVQModeloV;

        private TextView lblAdpVQSerialV;

        public TextView getLblAdpVQIdRequerimiento() {
            return lblAdpVQIdRequerimiento;
        }

        public void setLblAdpVQIdRequerimiento(TextView lblAdpVQIdRequerimiento) {
            this.lblAdpVQIdRequerimiento = lblAdpVQIdRequerimiento;
        }

        public TextView getLblAdpVQFechaCreacion() {
            return lblAdpVQFechaCreacion;
        }

        public void setLblAdpVQFechaCreacion(TextView lblAdpVQFechaCreacion) {
            this.lblAdpVQFechaCreacion = lblAdpVQFechaCreacion;
        }

        public TextView getLblAdpVQModeloV() {
            return lblAdpVQModeloV;
        }

        public void setLblAdpVQModeloV(TextView lblAdpVQModeloV) {
            this.lblAdpVQModeloV = lblAdpVQModeloV;
        }

        public TextView getLblAdpVQSerialV() {
            return lblAdpVQSerialV;
        }

        public void setLblAdpVQSerialV(TextView lblAdpVQSerialV) {
            this.lblAdpVQSerialV = lblAdpVQSerialV;
        }
    }
}
