package com.megasystem.suitepayment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DCliente;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DVenta;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Clientes;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.ArrayList;
import java.util.List;

public class ListClientes extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<Clientes> lstEmpleados;
    private ButtonFloat btnAdd;
    private int actionForm = 0;
    private Clientes objCliente;
    private Dialog dialog;
    private final int orderRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cliente);
        this.setTitle(R.string.list_clientes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DCliente dalEmpleado = new DCliente(ListClientes.this, Clientes.class);
        lstEmpleados = dalEmpleado.list();
        lvEmpleados = (ListView) findViewById(R.id.listView);
        btnAdd = (ButtonFloat) findViewById(R.id.add);
        lvEmpleados.setAdapter(new Adapter(ListClientes.this,lstEmpleados));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListClientes.this, com.megasystem.suitepayment.activity.Cliente.class);
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
    public class Adapter extends ArrayAdapter<Clientes> {

        private final Context context;

        public Adapter(Context context, List<Clientes> items) {
            super(context, R.layout.item_cliente, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_cliente, null);
            registerForContextMenu(view);
            Clientes obj = lstEmpleados.get(position);
            TextView nombre = (TextView) view.findViewById(R.id.tvEmployeeName);

            nombre.setText(obj.getNombre());
            view.setTag(R.id.add, obj);
            view.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        objCliente = (Clientes) v.getTag(R.id.add);
                        v.requestFocus();
                        ListClientes.this.openContextMenu(v);
                        return true;
                    }
                    return false;
                }
            });
            return view;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            DCliente dalEmpleado = new DCliente(ListClientes.this, Clientes.class);
            lstEmpleados = dalEmpleado.list();
            lvEmpleados.setAdapter(new Adapter(ListClientes.this,lstEmpleados));
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(objCliente.getNombre());
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.order, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            Intent intent;
            switch (item.getItemId()) {


                case R.id.venta:
                    intent = new Intent(ListClientes.this, com.megasystem.suitepayment.activity.Venta.class);
                    actionForm = 2;
                    intent.putExtra("cliente", objCliente);
                    intent.putExtra("actionForm", actionForm);
                    startActivityForResult(intent, orderRequest);
                    break;
                case R.id.cobros:
                    intent = new Intent(ListClientes.this, ListCobros.class);
                    intent.putExtra("cliente", objCliente);
                    intent.putExtra("actionForm", actionForm);
                    startActivityForResult(intent, orderRequest);
                    startActivity(intent);

                    break;
                case R.id.editar:
                   // Clientes emp = (Clientes) btn.getTag(R.id.details);
                    intent = new Intent(ListClientes.this, com.megasystem.suitepayment.activity.Cliente.class);
                    actionForm = 2;
                    intent.putExtra("cliente", objCliente);
                    intent.putExtra("actionForm", actionForm);
                    startActivityForResult(intent, orderRequest);
                    break;

                default:
                    break;
            }



        return false;
    }
}
