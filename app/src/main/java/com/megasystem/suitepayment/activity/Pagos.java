package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.util.Util;

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
    private ButtonRectangle btnSearch;
    private  Button btnCancel;
    private Dialog dialogEmpleado;
    private Empleado empleado;
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
        btnSearch = (ButtonRectangle) findViewById(R.id.btnSearch);
        btnCancel = (Button) findViewById(R.id.btnCancel);
       loadSpinner();
        loadSearchEmpleados();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pagos.this.finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void loadSearchEmpleados() {
        dialogEmpleado = new Dialog(Pagos.this);
        dialogEmpleado.setContentView(R.layout.search_empleado);
        dialogEmpleado.setTitle(getString(R.string.empleados));

        final EditText edtSearchP = (EditText) dialogEmpleado.findViewById(R.id.edtSearchP);
        edtSearchP.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.i(Application.tag,"Code:->"+edtSearchP.getText().toString());
                loadProducts( edtSearchP.getText().toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

    }
    private void loadProducts( String filter) {
        ListView list = (ListView) dialogEmpleado.findViewById(R.id.tableProducts);
        TextView txtNumber = (TextView) dialogEmpleado.findViewById(R.id.textNumber);
        DEmpleado data = new DEmpleado(this, com.megasystem.suitepayment.entity.sale.Empleado.class);
        List<Empleado> lstProducts = data.listBy( filter, new String[]{});
        txtNumber.setText(lstProducts.size() + " " + getString(R.string.registries));
        LazyAdapterProduct adapter = new LazyAdapterProduct(this, dialogEmpleado, lstProducts);
        list.setAdapter(adapter);
    }

    private class LazyAdapterProduct extends BaseAdapter {
        private Activity activity;
        private List<Empleado> data;
        private LayoutInflater inflater = null;
        private android.app.Dialog searchDialogEmpleado;

        public LazyAdapterProduct(Activity activity, android.app.Dialog searchProductDialog, List<Empleado> data) {
            this.activity = activity;
            this.data = data;
            this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.searchDialogEmpleado = searchProductDialog;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_empleado_saldo, null);
            }
            if (position % 2 == 0) {
                view.setBackgroundDrawable(activity.getResources().getDrawable(
                        R.drawable.list_selector_c));
            } else {
                view.setBackgroundDrawable(activity.getResources().getDrawable(
                        R.drawable.list_selector));
            }

            activity.registerForContextMenu(view);

            TextView txtName = (TextView) view.findViewById(R.id.empName);
            txtName.setText(data.get(position).getNombre());

            TextView txtSaldo = (TextView) view.findViewById(R.id.empSalary);
            txtSaldo.setText(Util.formatDouble(data.get(position).getSalario()));

            view.setTag(R.id.add, data.get(position));

            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        empleado = (Empleado) v.getTag(R.id.add);

                        searchDialogEmpleado.dismiss();
                        return true;
                    }
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });

            return view;
        }
    }

    public  void loadSpinner(){
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

    }
}
