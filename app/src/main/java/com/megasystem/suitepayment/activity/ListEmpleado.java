package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonFloat;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.List;

public class ListEmpleado extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<Empleado> lstEmpleados;
    private ButtonFloat btnAdd;
    private int actionForm = 0;
    private final int orderRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empleado);
        this.setTitle(R.string.list_empleados);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
        lstEmpleados = dalEmpleado.list();
        lvEmpleados = (ListView) findViewById(R.id.listView);
        btnAdd = (ButtonFloat) findViewById(R.id.add);
        lvEmpleados.setAdapter(new Adapter(ListEmpleado.this,lstEmpleados));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.Empleado.class);
                actionForm = 1;
                intent.putExtra("actionForm", actionForm);
                startActivityForResult(intent,orderRequest);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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
                    actionForm = 2;
                    intent.putExtra("empleado", emp);
                    intent.putExtra("actionForm", actionForm);
                    startActivityForResult(intent, orderRequest);
                   // startActivity(intent);

                }
            });

            return view;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
            lstEmpleados = dalEmpleado.list();
            lvEmpleados.setAdapter(new Adapter(ListEmpleado.this,lstEmpleados));
        }
    }
}
