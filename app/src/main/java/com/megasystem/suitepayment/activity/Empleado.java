package com.megasystem.suitepayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.Action;


public class Empleado extends AppCompatActivity {
    private EditText etName;
    private  EditText etAddress;
    private EditText etPhone;
    private EditText etSalary;
    private Button btnSave;
    private  Button btnCancel;
    private int actionForm=0;
    private com.megasystem.suitepayment.entity.sale.Empleado objEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        actionForm = getIntent().getExtras().getInt("actionForm");

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etDireccion);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSalary = (EditText) findViewById(R.id.etSalary);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel =(Button) findViewById(R.id.btnCancel);
        if(actionForm == 2){
            objEmpleado = (com.megasystem.suitepayment.entity.sale.Empleado) getIntent().getSerializableExtra("empleado");
            etName.setText(objEmpleado.getNombre());
            etAddress.setText(objEmpleado.getDireccion());
            etPhone.setText(objEmpleado.getTelefono());
            etSalary.setText(String.valueOf(objEmpleado.getSalario()));
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionForm == 1) {
                    DEmpleado dalEmpleado = new DEmpleado(Empleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
                    com.megasystem.suitepayment.entity.sale.Empleado empleado = new com.megasystem.suitepayment.entity.sale.Empleado();
                    empleado.setNombre(etName.getText().toString());
                    empleado.setDireccion(etAddress.getText().toString());
                    empleado.setTelefono(etPhone.getText().toString());
                    empleado.setSalario(Double.valueOf(etSalary.getText().toString()));
                    empleado.setAction(Action.Insert);
                    dalEmpleado.save(empleado);
                    //Toast.makeText(Empleado.this,"Guardado Correctamente.!",Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
                if (actionForm == 2) {
                    DEmpleado dalEmpleado = new DEmpleado(Empleado.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
                    objEmpleado.setNombre(etName.getText().toString());
                    objEmpleado.setDireccion(etAddress.getText().toString());
                    objEmpleado.setTelefono(etPhone.getText().toString());
                    objEmpleado.setSalario(Double.valueOf(etSalary.getText().toString()));
                    objEmpleado.setAction(Action.Update);
                    dalEmpleado.save(objEmpleado);
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });
    }
}
