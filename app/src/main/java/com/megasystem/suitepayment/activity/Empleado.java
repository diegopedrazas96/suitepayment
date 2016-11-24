package com.megasystem.suitepayment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.EditText;
import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;


public class Empleado extends AppCompatActivity {
    private EditText etName;
    private  EditText etAddress;
    private EditText etPhone;
    private EditText etSalary;
    private Button btnSave;
    private  Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etDireccion);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSalary = (EditText) findViewById(R.id.etSalary);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel =(Button) findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DEmpleado dalEmpleado = new DEmpleado(Empleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
                com.megasystem.suitepayment.entity.sale.Empleado empleado = new com.megasystem.suitepayment.entity.sale.Empleado();
                empleado.setNombre(etName.getText().toString());
                empleado.setDireccion(etAddress.getText().toString());
                empleado.setTelefono(etPhone.getText().toString());
                empleado.setSalario(Double.valueOf(etSalary.getText().toString()));
                empleado.setAction(Action.Insert);
                dalEmpleado.save(empleado);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
