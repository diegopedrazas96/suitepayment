package com.megasystem.suitepayment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import com.megasystem.suitepayment.R;

public class Gastos extends AppCompatActivity {
    private EditText etDate;
    private Spinner spSpendingType;
    private EditText etDescripcion;
    private EditText etMonto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        etDate = (EditText) findViewById(R.id.etDate);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spSpendingType = (Spinner) findViewById(R.id.spSpendingType);

    }
}
