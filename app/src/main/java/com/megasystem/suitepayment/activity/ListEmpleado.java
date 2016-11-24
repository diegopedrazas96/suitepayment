package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gc.materialdesign.views.ButtonFloat;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.List;

public class ListEmpleado extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<Empleado> lstEmpleados;
    private ButtonFloat btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empleado);
        DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
        lstEmpleados = dalEmpleado.list();
        lvEmpleados = (ListView) findViewById(R.id.listView);
        btnAdd = (ButtonFloat) findViewById(R.id.add);
        lvEmpleados.setAdapter(new Adapter(ListEmpleado.this,lstEmpleados));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.Empleado.class);
                startActivity(intent);
            }
        });
    }

    public class Adapter extends ArrayAdapter<Empleado> {

        private final Context context;

        public Adapter(Context context, List<Empleado> items) {
            super(context, R.layout.item_empleado, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_empleado, null);
            Empleado obj = lstEmpleados.get(position);
            TextView nombre = (TextView) view.findViewById(R.id.tvEmployeeName);
            final Button btn = (Button) view.findViewById(R.id.details);
            nombre.setText(obj.getNombre());
            btn.setTag(R.id.details, obj);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Empleado emp = (Empleado) btn.getTag(R.id.details);

                    Intent intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.Empleado.class);
                    //intent.putExtra(R.id.details,emp);
                    startActivity(intent);

                }
            });

            return view;
        }

    }
}
