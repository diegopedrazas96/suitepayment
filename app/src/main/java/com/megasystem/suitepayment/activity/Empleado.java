package com.megasystem.suitepayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gc.materialdesign.views.Button;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.util.List;


public class Empleado extends AppCompatActivity {
    private EditText etName;
    private  EditText etAddress;
    private EditText etPhone;
    private EditText etSalary;
    private EditText etEstado;
    private Button btnSave;
    private  Button btnCancel;
    private Spinner spTipo;
    private List<PsClasificador> lstPsTipo;
    private int actionForm=0;
    private com.megasystem.suitepayment.entity.sale.Empleado objEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);
        this.setTitle(R.string.empleado);
        actionForm = getIntent().getExtras().getInt("actionForm");

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etDireccion);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSalary = (EditText) findViewById(R.id.etSalary);
        etEstado = (EditText) findViewById(R.id.etEstado);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel =(Button) findViewById(R.id.btnCancel);
        loadSpinner();
        if(actionForm == 2){
            objEmpleado = (com.megasystem.suitepayment.entity.sale.Empleado) getIntent().getSerializableExtra("empleado");
            etName.setText(objEmpleado.getNombre());
            etAddress.setText(objEmpleado.getDireccion());
            etPhone.setText(objEmpleado.getTelefono());
            spTipo.setSelection(selectSpinner());
            etEstado.setText(Util.formatDoubleWithOuTDecimal(objEmpleado.getMontoAcumulado()));
            etSalary.setText(Util.formatDoubleWithOuTDecimal(objEmpleado.getSalario()));
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
                    empleado.setTipoIdc(lstPsTipo.get(spTipo.getSelectedItemPosition()).getId());
                    empleado.setAction(Action.Insert);
                    empleado.setMontoAcumulado(0D);
                    if (empleado.getTipoIdc() == EnumClasificadores.PorObra.getValor()){
                        empleado.setMontoAcumulado(empleado.getSalario());
                    }
                    empleado.setEstado(1);
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
                    objEmpleado.setTipoIdc(lstPsTipo.get(spTipo.getSelectedItemPosition()).getId());
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
    private int selectSpinner(){
        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        lstPsTipo =  dalProduct.listbyMsClasifier(5L);
        String[] tipoArray = new String[lstPsTipo.size()];
        int variable= 0;
        //int i = 1;
        for (int i = 0; i < lstPsTipo.size()-1 ;i++) {
            if (objEmpleado.getTipoIdc().longValue() == lstPsTipo.get(i).getId().longValue() ){
                variable= i;
            }
        }
        return variable;
        //return i-1;
    }
    private void loadSpinner(){
        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        lstPsTipo =  dalProduct.listbyMsClasifier(5L);
        String[] tipoArray = new String[lstPsTipo.size()];
        int i = 0;
        for (PsClasificador obj : lstPsTipo) {
            tipoArray[i] = obj.getDescripcion();
            i++;
        }
        ArrayAdapter<String> sCityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, tipoArray);
        sCityAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spTipo.setAdapter(sCityAdapter);
    }
}
