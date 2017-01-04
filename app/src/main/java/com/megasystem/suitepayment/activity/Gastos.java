package com.megasystem.suitepayment.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DGasto;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Gastos extends AppCompatActivity {
    private EditText etDate;
    private Spinner spSpendingType;
    private EditText etDescripcion;
    private EditText etMonto;
    private ButtonRectangle btnGuardar;
    private ButtonRectangle btnCancelar;
    private Button btnDate;
    private Dialog dateDialog;
    private List<PsClasificador> lstPsGastos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        this.setTitle(R.string.gastos);
        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etDate.setEnabled(false);

        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spSpendingType = (Spinner) findViewById(R.id.spSpendingType);
        btnGuardar = (ButtonRectangle) findViewById(R.id.btnSave);
        btnCancelar = (ButtonRectangle) findViewById(R.id.btnCancel);
        try {
            btnDate = (Button) findViewById(R.id.btnDate);
        }catch (Exception e){
            Log.e("GASTOS",e.toString());
        }

        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        lstPsGastos =  dalProduct.listbyMsClasifier(3L);
        loadDialogDate();
        String[] gastoArray = new String[lstPsGastos.size()];
        int i = 0;
        for (PsClasificador obj : lstPsGastos) {
            gastoArray[i] = obj.getDescripcion();
             i++;
        }
        ArrayAdapter<String> sCityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, gastoArray);
        sCityAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spSpendingType.setAdapter(sCityAdapter);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
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
    private void loadDialogDate(){
        dateDialog = new Dialog(Gastos.this);
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
                etDate.setText(fecha1);
                dateDialog.dismiss();
            }
        });

    }
    private void limpiar(){
        etDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etDescripcion.setText("");
        etMonto.setText("");
    }
}
