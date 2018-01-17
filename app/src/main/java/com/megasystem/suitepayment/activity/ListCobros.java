package com.megasystem.suitepayment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DCliente;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.data.sale.DVenta;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Venta;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListCobros extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<com.megasystem.suitepayment.entity.sale.Venta> lstEmpleados;
    private ButtonFloat btnAdd;
    private int actionForm = 0;
    private Clientes objCliente;
    private Dialog dialog;
    private com.megasystem.suitepayment.entity.sale.Venta objVenta;
    private final int orderRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venta);
        this.setTitle(R.string.list_ventas);
        actionForm = getIntent().getExtras().getInt("actionForm");
        objCliente = (com.megasystem.suitepayment.entity.sale.Clientes) getIntent().getSerializableExtra("cliente");
        loadList();

    }
    public void loadList(){
        DVenta dalEmpleado = new DVenta(ListCobros.this, com.megasystem.suitepayment.entity.sale.Venta.class);
        lstEmpleados = dalEmpleado.list(objCliente.getId());
        lvEmpleados = (ListView) findViewById(R.id.listView);
        lvEmpleados.setAdapter(new Adapter(ListCobros.this, lstEmpleados));
    }
    public class Adapter extends ArrayAdapter<com.megasystem.suitepayment.entity.sale.Venta> {

        private final Context context;

        public Adapter(Context context, List<com.megasystem.suitepayment.entity.sale.Venta> items) {
            super(context, R.layout.item_lista_pagos, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_lista_pagos, null);
            registerForContextMenu(view);
            com.megasystem.suitepayment.entity.sale.Venta obj = lstEmpleados.get(position);
            TextView nombre = (TextView) view.findViewById(R.id.tvEmployeeName);
            TextView fecha = (TextView) view.findViewById(R.id.tvDate);
            TextView price = (TextView) view.findViewById(R.id.price);
            nombre.setText(obj.getDescripcion());
            price.setText(Util.formatDouble(obj.getMontoSaldo()));
            fecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha()));
            view.setTag(R.id.add, obj);

            view.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        objVenta = (com.megasystem.suitepayment.entity.sale.Venta) v.getTag(R.id.add);
                        v.requestFocus();
                        ListCobros.this.openContextMenu(v);
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
        menu.setHeaderTitle(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(objVenta.getFecha()) + " - " + objVenta.getDescripcion());
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.venta, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            Intent intent;
            switch (item.getItemId()) {
                case R.id.cobrar:
                    dialog = new Dialog(ListCobros.this);
                    dialog.setContentView(R.layout.dialog_cobro);
                    dialog.setTitle(getString(R.string.cobros));
                    final EditText etGroup = (EditText) dialog.findViewById(R.id.etMonto);
                    ButtonRectangle btnSave = (ButtonRectangle) dialog.findViewById(R.id.btnSave);
                    ButtonRectangle btnCancel = (ButtonRectangle) dialog.findViewById(R.id.btnCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DVenta dalVenta = new DVenta(ListCobros.this, com.megasystem.suitepayment.entity.sale.Venta.class);
                            objVenta.setMontoPagado(objVenta.getMontoPagado() + Double.parseDouble(etGroup.getText().toString()));
                            objVenta.setMontoSaldo(objVenta.getMontoSaldo() - Double.parseDouble(etGroup.getText().toString()));
                            objVenta.setAction(Action.Update);
                            dalVenta.save(objVenta);
                            dialog.dismiss();
                            Toast.makeText(ListCobros.this, "Guardado Correctamente.!", Toast.LENGTH_SHORT).show();
                            loadList();
                        }
                    });
                    dialog.show();
                    break;
                case R.id.anular:
                    new AlertDialog.Builder(ListCobros.this).setTitle(getString(R.string.message))
                            .setMessage(getString(R.string.question_anulate))
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    DVenta dalVenta = new DVenta(ListCobros.this, com.megasystem.suitepayment.entity.sale.Venta.class);
                                    objVenta.setEstado(0);
                                    objVenta.setAction(Action.Update);
                                    dalVenta.save(objVenta);
                                    loadList();

                                }
                            }).create().show();


                    break;

                default:
                    break;
            }



        return false;
    }
}
