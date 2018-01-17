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
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DPagoEmpleado;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.data.sale.DVenta;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.entity.sale.PagoEmpleado;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListEmpleado extends AppCompatActivity {
    private ListView lvEmpleados;
    private List<Empleado> lstEmpleados;
    private TextView totalEmpleado;

    private ButtonFloat btnAdd;
    private int actionForm = 0;
    private final int orderRequest = 1;
    private Empleado objEmpleado;
    private Dialog dialog;
    private double totalSaldos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empleado);
        this.setTitle(R.string.list_empleados);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvEmpleados = (ListView) findViewById(R.id.listView);
        totalEmpleado = (TextView) findViewById(R.id.etTotalCliente);
        btnAdd = (ButtonFloat) findViewById(R.id.add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.Empleado.class);
                actionForm = 1;
                intent.putExtra("actionForm", actionForm);
                startActivityForResult(intent,orderRequest);
            }
        });
        loadEmpleados();
    }
    private void loadEmpleados(){
        totalSaldos = 0;
        DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
        lstEmpleados = dalEmpleado.listAll();
        loadClasifier();
        lvEmpleados.setAdapter(new Adapter(ListEmpleado.this, lstEmpleados));
        totalEmpleado.setText(Util.formatDouble(totalSaldos));
    }

    private void loadClasifier() {
        for (Empleado obj : lstEmpleados){
            DPsClasificador dalClasifier = new DPsClasificador(ListEmpleado.this,PsClasificador.class);
            obj.setTipo(dalClasifier.getById(obj.getTipoIdc()));
            totalSaldos += obj.getMontoAcumulado();
        }
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
            if (obj.getEstado().intValue() == 0) {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_inactive));
            } else {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_active));
            }
            registerForContextMenu(view);
            TextView nombre = (TextView) view.findViewById(R.id.tvEmployeeName);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView price = (TextView) view.findViewById(R.id.price);
            nombre.setText(obj.getNombre());
            type.setText(obj.getTipo().getDescripcion());
            price.setText(Util.formatDouble(obj.getMontoAcumulado()));
            view.setTag(R.id.add, obj);
            view.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        objEmpleado = (Empleado) v.getTag(R.id.add);
                        v.requestFocus();
                        ListEmpleado.this.openContextMenu(v);
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
            DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
            lstEmpleados = dalEmpleado.list();
            lvEmpleados.setAdapter(new Adapter(ListEmpleado.this,lstEmpleados));
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(objEmpleado.getNombre());
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.empleados, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {


            case R.id.pagar:
                dialog = new Dialog(ListEmpleado.this);
                dialog.setContentView(R.layout.activity_pagoempleado);
                dialog.setTitle(getString(R.string.pagar));
                final EditText etMonto = (EditText) dialog.findViewById(R.id.etMonto);
                EditText etEmpleado = (EditText) dialog.findViewById(R.id.etEmpleado);
                final EditText etDescripcion = (EditText) dialog.findViewById(R.id.etDescripcion);
                final EditText etDate = (EditText) dialog.findViewById(R.id.etDate);
                etEmpleado.setText(objEmpleado.getNombre());
                etDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
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
                        DPagoEmpleado dalVenta = new DPagoEmpleado(ListEmpleado.this, com.megasystem.suitepayment.entity.sale.PagoEmpleado.class);
                        PagoEmpleado objPago = new PagoEmpleado();
                        objPago.setMonto(Double.valueOf(etMonto.getText().toString()));
                        objPago.setDescripcion(etDescripcion.getText().toString());
                        objPago.setFecha(new Date());
                        objPago.setEmpleadoId(objEmpleado.getId());
                        objPago.setAction(Action.Insert);
                        objPago.setEstado(1);
                        dalVenta.save(objPago);
                        DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this,Empleado.class);
                        objEmpleado.setMontoAcumulado(objEmpleado.getMontoAcumulado() - objPago.getMonto());
                        objEmpleado.setAction(Action.Update);
                        dalEmpleado.save(objEmpleado);
                        dialog.dismiss();
                        Toast.makeText(ListEmpleado.this, "Guardado Correctamente.!", Toast.LENGTH_SHORT).show();
                        loadEmpleados();
                    }
                });
                dialog.show();
                break;
            case R.id.habilitar:
                new AlertDialog.Builder(ListEmpleado.this).setTitle(getString(R.string.message))
                        .setMessage(getString(R.string.question_activate_employee) + " " + objEmpleado.getNombre())
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this,Empleado.class);
                                if (objEmpleado.getEstado().intValue() == 0){
                                    objEmpleado.setEstado(1);
                                }else{
                                    objEmpleado.setEstado(0);
                                }
                                objEmpleado.setAction(Action.Update);
                                dalEmpleado.save(objEmpleado);
                                loadEmpleados();

                            }
                        }).create().show();
                break;
            case R.id.ver:
                // Clientes emp = (Clientes) btn.getTag(R.id.details);
                intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.ListPagoEmpleado.class);
                actionForm = 2;
                intent.putExtra("empleado", objEmpleado);
                intent.putExtra("actionForm", actionForm);
                startActivityForResult(intent, orderRequest);
                break;
            case R.id.bono:
                dialog = new Dialog(ListEmpleado.this);
                dialog.setContentView(R.layout.dialog_cobro);
                dialog.setTitle(getString(R.string.bono));
                final EditText etGroup = (EditText) dialog.findViewById(R.id.etMonto);
                ButtonRectangle btnSaveDialog = (ButtonRectangle) dialog.findViewById(R.id.btnSave);
                ButtonRectangle btnCancelDialog = (ButtonRectangle) dialog.findViewById(R.id.btnCancel);
                btnCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnSaveDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DEmpleado dalEmpleado = new DEmpleado(ListEmpleado.this,Empleado.class);
                        objEmpleado.setMontoAcumulado(objEmpleado.getMontoAcumulado() + Double.parseDouble(etGroup.getText().toString()));
                        objEmpleado.setAction(Action.Update);
                        dalEmpleado.save(objEmpleado);
                        dialog.dismiss();
                        Toast.makeText(ListEmpleado.this, "Bono Guardado Correctamente.!", Toast.LENGTH_SHORT).show();
                        loadEmpleados();
                    }
                });
                dialog.show();
                break;
            case R.id.editar:
                // Clientes emp = (Clientes) btn.getTag(R.id.details);
                intent = new Intent(ListEmpleado.this, com.megasystem.suitepayment.activity.Empleado.class);
                actionForm = 2;
                intent.putExtra("empleado", objEmpleado);
                intent.putExtra("actionForm", actionForm);
                startActivityForResult(intent, orderRequest);
                break;
            default:
                break;
        }



        return false;
    }
}
