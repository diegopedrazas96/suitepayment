package com.megasystem.suitepayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DCliente;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.util.Util;


public class Cliente extends AppCompatActivity {
    private EditText etName;
    private  EditText etAddress;
    private EditText etPhone;
    private Button btnSave;
    private  Button btnCancel;
    private int actionForm=0;
    private com.megasystem.suitepayment.entity.sale.Clientes objEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        this.setTitle(R.string.cliente);
        actionForm = getIntent().getExtras().getInt("actionForm");

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etDireccion);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel =(Button) findViewById(R.id.btnCancel);
        if(actionForm == 2){
            objEmpleado = (com.megasystem.suitepayment.entity.sale.Clientes) getIntent().getSerializableExtra("cliente");
            etName.setText(objEmpleado.getNombre());
            etAddress.setText(objEmpleado.getDireccion());
            etPhone.setText(objEmpleado.getTelefono());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionForm == 1) {
                    DCliente dalEmpleado = new DCliente(Cliente.this, com.megasystem.suitepayment.entity.sale.Clientes.class);
                    com.megasystem.suitepayment.entity.sale.Clientes empleado = new com.megasystem.suitepayment.entity.sale.Clientes();
                    empleado.setNombre(etName.getText().toString());
                    empleado.setDireccion(etAddress.getText().toString());
                    empleado.setTelefono(etPhone.getText().toString());
                    empleado.setAction(Action.Insert);
                    empleado.setEstado(1);
                    dalEmpleado.save(empleado);
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
                if (actionForm == 2) {
                    DEmpleado dalEmpleado = new DEmpleado(Cliente.this, com.megasystem.suitepayment.entity.sale.Clientes.class);
                    objEmpleado.setNombre(etName.getText().toString());
                    objEmpleado.setDireccion(etAddress.getText().toString());
                    objEmpleado.setTelefono(etPhone.getText().toString());
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
