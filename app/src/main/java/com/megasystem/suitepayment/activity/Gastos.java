package com.megasystem.suitepayment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DGasto;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;
import com.megasystem.suitepayment.entity.sale.Gasto;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.util.Date;
import java.util.List;

public class Gastos extends AppCompatActivity {
    private EditText etDate;
    private Spinner spSpendingType;
    private EditText etDescripcion;
    private EditText etMonto;
    private Button btnGuardar;
    private Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        etDate = (EditText) findViewById(R.id.etDate);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spSpendingType = (Spinner) findViewById(R.id.spSpendingType);
        btnGuardar = (Button) findViewById(R.id.btnSave);
        btnCancelar = (Button) findViewById(R.id.btnCancel);
        DPsClasificador classifiers = new DPsClasificador(Gastos.this, DPsClasificador.class);
        List<PsClasificador> gastos = classifiers.list(EnumClasificadores.Gasto.getValor());
        String[] gastoArray = new String[gastos.size()];
        int i = 0;
        for (PsClasificador obj : gastos) {
            gastoArray[i] = obj.getDescripcion();
        }
        ArrayAdapter<String> sCityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gastoArray);
        sCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpendingType.setAdapter(sCityAdapter);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DGasto dalGasto = new DGasto(Gastos.this, com.megasystem.suitepayment.entity.sale.Gasto.class);
                com.megasystem.suitepayment.entity.sale.Gasto gasto = new com.megasystem.suitepayment.entity.sale.Gasto();
                gasto.setFecha(new Date());
                gasto.setDescripcion(etDescripcion.getText().toString());
                gasto.setMonto(Double.valueOf(etMonto.getText().toString()));
                gasto.setTipoIdc(spSpendingType.getSelectedItemId());
                gasto.setAction(Action.Insert);
                dalGasto.save(gasto);

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
