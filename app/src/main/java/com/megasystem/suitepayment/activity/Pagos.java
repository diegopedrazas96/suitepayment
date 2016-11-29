package com.megasystem.suitepayment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Pagos extends AppCompatActivity {
    private EditText etFecha;
    private EditText etEmpleado;
    private EditText etMonto;
    private Spinner spPeriodType;
    private Spinner spMonthType;
    private Button btnSave;
    private  Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        etFecha = (EditText) findViewById(R.id.etDate);
        etFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etFecha.setEnabled(false);
        etEmpleado = (EditText) findViewById(R.id.etEmpleado);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spPeriodType = (Spinner) findViewById(R.id.spPeriodType);
        spMonthType = (Spinner) findViewById(R.id.spMonthType);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        DPsClasificador classifiers = new DPsClasificador(Pagos.this, PsClasificador.class);
        List<PsClasificador> periodo = classifiers.list(EnumClasificadores.Periodo.getValor());
        List<PsClasificador> gestion = classifiers.list(EnumClasificadores.Gestion.getValor());
        String[] periodoArray = new String[periodo.size()];
        String[] gestionArray = new String[gestion.size()];
        int i = 0;
        for (PsClasificador obj : periodo) {
            periodoArray[i] = obj.getDescripcion();
            i++;
        }
         i = 0;
        for (PsClasificador obj : gestion) {
            gestionArray[i] = obj.getDescripcion();
            i++;
        }
        ArrayAdapter<String> sPeriodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periodoArray);
        sPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonthType.setAdapter(sPeriodAdapter);
        ArrayAdapter<String> sGestionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gestionArray);
        sGestionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriodType.setAdapter(sGestionAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public  void loadSpinner(){


    }
}
