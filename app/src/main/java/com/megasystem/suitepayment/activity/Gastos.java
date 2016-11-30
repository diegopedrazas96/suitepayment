package com.megasystem.suitepayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DGasto;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Gastos extends AppCompatActivity {
    private EditText etDate;
    private Spinner spSpendingType;
    private EditText etDescripcion;
    private EditText etMonto;
    private Button btnGuardar;
    private Button btnCancelar;
    private List<PsClasificador> lstPsGastos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etDate.setEnabled(false);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spSpendingType = (Spinner) findViewById(R.id.spSpendingType);
        btnGuardar = (Button) findViewById(R.id.btnSave);
        btnCancelar = (Button) findViewById(R.id.btnCancel);
        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        lstPsGastos =  dalProduct.listbyMsClasifier(3L);
        String[] gastoArray = new String[lstPsGastos.size()];
        int i = 0;
        for (PsClasificador obj : lstPsGastos) {
            gastoArray[i] = obj.getDescripcion();
             i++;
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
                gasto.setTipoIdc(lstPsGastos.get(spSpendingType.getSelectedItemPosition()).getId());
                gasto.setAction(Action.Insert);
                dalGasto.save(gasto);
                limpiar();
                Toast.makeText(Gastos.this,getString(R.string.message_save),Toast.LENGTH_SHORT).show();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gastos.this,Main.class);
                startActivity(intent);
            }
        });

    }
    private void limpiar(){
        etDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etDescripcion.setText("");
        etMonto.setText("");
    }
}
