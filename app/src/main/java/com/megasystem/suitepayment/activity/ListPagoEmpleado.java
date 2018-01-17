package com.megasystem.suitepayment.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DPagoEmpleado;
import com.megasystem.suitepayment.data.sale.DVenta;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.entity.sale.PagoEmpleado;
import com.megasystem.suitepayment.entity.sale.Venta;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListPagoEmpleado extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<com.megasystem.suitepayment.entity.sale.PagoEmpleado> lstEmpleados;
    private ButtonFloat btnAdd;
    private int actionForm = 0;
    private com.megasystem.suitepayment.entity.sale.Empleado objCliente;
    private Dialog dialog;
    private com.megasystem.suitepayment.entity.sale.PagoEmpleado objVenta;
    private final int orderRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venta);
        this.setTitle(R.string.list_pagos);
        lvEmpleados = (ListView) findViewById(R.id.listView);
        actionForm = getIntent().getExtras().getInt("actionForm");
        objCliente = (com.megasystem.suitepayment.entity.sale.Empleado) getIntent().getSerializableExtra("empleado");
        loadList();

    }
    public void loadList(){
        DPagoEmpleado dalEmpleado = new DPagoEmpleado(ListPagoEmpleado.this, com.megasystem.suitepayment.entity.sale.PagoEmpleado.class);
        lstEmpleados = dalEmpleado.listByEmpleado(objCliente.getId());
        if (lstEmpleados.size()>0){
            lvEmpleados.setAdapter(new Adapter(ListPagoEmpleado.this, lstEmpleados));
        }

    }
    public class Adapter extends ArrayAdapter<com.megasystem.suitepayment.entity.sale.PagoEmpleado> {

        private final Context context;

        public Adapter(Context context, List<com.megasystem.suitepayment.entity.sale.PagoEmpleado> items) {
            super(context, R.layout.item_cliente, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_lista_pagos, null);
            registerForContextMenu(view);
            com.megasystem.suitepayment.entity.sale.PagoEmpleado obj = lstEmpleados.get(position);
            TextView nombre = (TextView) view.findViewById(R.id.tvEmployeeName);
            TextView fecha = (TextView) view.findViewById(R.id.tvDate);
            TextView price = (TextView) view.findViewById(R.id.price);
            nombre.setText(obj.getDescripcion());
            price.setText(Util.formatDouble(obj.getMonto()));
            fecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha()));
            view.setTag(R.id.add, obj);

            view.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        objVenta = (com.megasystem.suitepayment.entity.sale.PagoEmpleado) v.getTag(R.id.add);
                        v.requestFocus();
                        ListPagoEmpleado.this.openContextMenu(v);
                        return true;
                    }
                    return false;
                }
            });
            return view;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(objVenta.getId() + "-" + objVenta.getDescripcion());
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.pagos, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            Intent intent;
            switch (item.getItemId()) {
                case R.id.anular:
                    new AlertDialog.Builder(ListPagoEmpleado.this).setTitle(getString(R.string.message))
                            .setMessage(getString(R.string.question_anulate))
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    DVenta dalVenta = new DVenta(ListPagoEmpleado.this, PagoEmpleado.class);
                                    objVenta.setEstado(0);
                                    objVenta.setAction(Action.Update);
                                    dalVenta.save(objVenta);
                                    objCliente.setMontoAcumulado(objVenta.getMonto() + objCliente.getMontoAcumulado());
                                    objCliente.setAction(Action.Update);
                                    DEmpleado dalEmpleado = new DEmpleado(ListPagoEmpleado.this, Empleado.class);
                                    dalEmpleado.save(objCliente);
                                    loadList();
                                    Toast.makeText(ListPagoEmpleado.this,"Anulado correctamente.!",Toast.LENGTH_LONG).show();

                                }
                            }).create().show();


                    break;

                default:
                    break;
            }



        return false;
    }
}
