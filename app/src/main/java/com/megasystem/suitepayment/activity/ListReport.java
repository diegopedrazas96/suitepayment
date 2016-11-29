package com.megasystem.suitepayment.activity;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.megasystem.suitepayment.R;

import java.util.ArrayList;
import java.util.List;

public class ListReport extends AppCompatActivity {
    private ListView lvItems;
    private ButtonFloat btnadd;
    private List<String> lstOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empleado);
        lvItems = (ListView) findViewById(R.id.listView);
        btnadd = (ButtonFloat) findViewById(R.id.add);
        btnadd.setVisibility(View.GONE);
        lstOptions = new ArrayList<String>();
        lstOptions.add("Generar Planilla");
        lstOptions.add("Reporte Gastos");
        lstOptions.add("Reporte de Pagos");
        lvItems.setAdapter(new Adapter(ListReport.this, lstOptions));
    }

    public class Adapter extends ArrayAdapter<String> {

        private final Context context;

        public Adapter(Context context, List<String> items) {
            super(context, R.layout.item_empleado, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_basic, null);
            String obj = lstOptions.get(position);
            final TextView nombre = (TextView) view.findViewById(R.id.name);
            nombre.setText(obj);
            nombre.setTag(R.id.details, obj);
            nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String emp = (String) nombre.getTag(R.id.details);
                    if (emp.equals("Generar Planilla")) {
                        intent = new Intent(ListReport.this,ReporteGastos.class);
                        startActivity(intent);
                    }
                }
            });
            return view;
        }

    }
}
