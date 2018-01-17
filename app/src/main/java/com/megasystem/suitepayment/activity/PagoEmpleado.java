package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPago;
import com.megasystem.suitepayment.data.sale.DPagoEmpleado;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;
import com.megasystem.suitepayment.entity.sale.HistorialPagos;
import com.megasystem.suitepayment.entity.sale.Pago;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PagoEmpleado extends AppCompatActivity {
    private EditText etFecha;
    private EditText etEmpleado;
    private EditText etMonto;
    private EditText etDescripcion;
    private ButtonRectangle btnSave;
    private Button btnDate;
    private ButtonRectangle btnCancel;
    private Empleado empleado;
    private Dialog dateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        this.setTitle(R.string.pagos);
        etFecha = (EditText) findViewById(R.id.etDate);
        etFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etFecha.setEnabled(false);
        etEmpleado = (EditText) findViewById(R.id.etEmpleado);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        btnSave = (ButtonRectangle) findViewById(R.id.btnSave);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnCancel = (ButtonRectangle) findViewById(R.id.btnCancel);
        empleado = (Empleado) getIntent().getSerializableExtra("empleado");
        loadDialogDate();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DPagoEmpleado dalPago = new DPagoEmpleado(PagoEmpleado.this, com.megasystem.suitepayment.entity.sale.PagoEmpleado.class);
                com.megasystem.suitepayment.entity.sale.PagoEmpleado pago = new com.megasystem.suitepayment.entity.sale.PagoEmpleado();
                pago.setFecha(new Date());
                pago.setEmpleadoId(empleado.getId());
                pago.setMonto(Double.valueOf(etMonto.getText().toString()));
                pago.setDescripcion(etDescripcion.getText().toString());
                pago.setEstado(1);
                // pago.setDescripcion(e);
                pago.setAction(Action.Insert);
                dalPago.save(pago);
                Toast.makeText(PagoEmpleado.this, "No se pudo procesar el pago, el empleado no cuenta con un registro de pago para este mes", Toast.LENGTH_LONG).show();


            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PagoEmpleado.this.finish();
            }
        });

    }
    private void loadDialogDate(){
        dateDialog = new Dialog(PagoEmpleado.this);
        dateDialog.setContentView(R.layout.sale_date);
        dateDialog.setTitle(getString(R.string.date_title));

        final DatePicker deliveryDate = (DatePicker) dateDialog.findViewById(R.id.deliveryDate);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.HOUR, 24);

        deliveryDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        final ButtonRectangle btnCancelDate = (ButtonRectangle) dateDialog.findViewById(R.id.btnCancel);
        btnCancelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.cancel();
            }
        });

        final ButtonRectangle btnSaveDate = (ButtonRectangle) dateDialog.findViewById(R.id.btnSave);
        btnSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha1 = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Util.getDateFromDatePicket(deliveryDate)).toString();
                etFecha.setText(fecha1);
                dateDialog.dismiss();
            }
        });

    }

    private void  limpiarCampos(){
        etEmpleado.setText("");
        etMonto.setText("");

    }



}
