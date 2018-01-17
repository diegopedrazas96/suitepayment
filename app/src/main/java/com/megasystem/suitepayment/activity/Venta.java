package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.megasystem.suitepayment.data.sale.DCliente;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPago;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.data.sale.DVenta;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Clientes;
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

public class Venta extends AppCompatActivity {
    private EditText etFecha;
    private EditText etCliente;
    private EditText etMonto;
    private EditText etAnticipo;
    private EditText etDescripcion;
    private int actionForm=0;
    private ButtonRectangle btnSave;
    private Button btnSearch;
    private Button btnDate;
    private  ButtonRectangle btnCancel;
    private Clientes objCliente;
    private Dialog dateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        this.setTitle(R.string.venta);
        etFecha = (EditText) findViewById(R.id.etDate);
        etFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etFecha.setEnabled(false);
        etCliente = (EditText) findViewById(R.id.etCliente);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        etAnticipo = (EditText) findViewById(R.id.etAnticipo);
        btnSave = (ButtonRectangle) findViewById(R.id.btnSave);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnCancel = (ButtonRectangle) findViewById(R.id.btnCancel);
        actionForm = getIntent().getExtras().getInt("actionForm");
        objCliente = (com.megasystem.suitepayment.entity.sale.Clientes) getIntent().getSerializableExtra("cliente");
        etCliente.setText(objCliente.getNombre());
        etCliente.setEnabled(false);
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

                    DVenta dalVenta = new DVenta(Venta.this,com.megasystem.suitepayment.entity.sale.Venta.class);
                    com.megasystem.suitepayment.entity.sale.Venta venta = new com.megasystem.suitepayment.entity.sale.Venta();
                    venta.setFecha(new Date());
                 venta.setClienteId(objCliente.getId());
                  venta.setMontoVenta(Double.valueOf(etMonto.getText().toString()));
                    venta.setMontoPagado(Double.valueOf(etAnticipo.getText().toString()));
                    venta.setMontoSaldo(Double.valueOf(etMonto.getText().toString()) - Double.valueOf(etAnticipo.getText().toString()));
                    venta.setDescripcion(etDescripcion.getText().toString());
                    venta.setEstado(1);
                    venta.setAction(Action.Insert);
                    dalVenta.save(venta);
                    Toast.makeText(Venta.this,"Venta registrada correctamente para " + objCliente.getNombre(),Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();




            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venta.this.finish();
            }
        });

    }
    private void loadDialogDate(){
        dateDialog = new Dialog(Venta.this);
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
        etCliente.setText("");
        etMonto.setText("");

    }



}
