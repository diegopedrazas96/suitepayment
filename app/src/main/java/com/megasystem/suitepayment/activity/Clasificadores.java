package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DMsClasificador;
import com.megasystem.suitepayment.entity.sale.MsClasificador;

import java.util.List;

public class Clasificadores extends AppCompatActivity {
    private ListView lvMsClasifier ;
    private ListView lvPsClasifier ;
    private List<MsClasificador> lstClasificador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificadores);
        lvMsClasifier = (ListView) findViewById(R.id.list);
        lvPsClasifier = (ListView) findViewById(R.id.list2);
        DMsClasificador dalMsClasificador = new DMsClasificador(this,MsClasificador.class);
        lstClasificador = dalMsClasificador.list();

        lvMsClasifier.setAdapter(new Adapter(Clasificadores.this,lstClasificador));
        //lvMsClasifier.setAdapter();
    }


    public class Adapter extends ArrayAdapter<MsClasificador> {

        private final Context context;

        public Adapter(Context context, List<MsClasificador> items) {
            super(context, R.layout.item_syncronize, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_device, null);
            TextView txtCode = (TextView) view.findViewById(R.id.mac);
            TextView txtName = (TextView) view.findViewById(R.id.name);
            MsClasificador obj = lstClasificador.get(position);
            txtName.setText(obj.getDescripcion());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return view;
        }

    }
}
