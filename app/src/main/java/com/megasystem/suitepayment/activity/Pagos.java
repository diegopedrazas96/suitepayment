package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPago;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Pagos extends AppCompatActivity {
    private EditText etFecha;
    private EditText etEmpleado;
    private EditText etMonto;
    private EditText etSaldo;
    private TextView tvSaldo;
    private EditText etDescripcion;
    private int actionForm=0;
    private ButtonRectangle btnSave;
    private Button btnSearch;
    private Button btnDate;
    private  ButtonRectangle btnCancel;
    private Dialog dialogEmpleado;
    private Empleado empleado;
    private Spinner spPeriodType;
    private Spinner spMonthType;
    private Long gestionIdc;
    private Long periodoIdc;
    private   List<PsClasificador> periodo;
    private List<PsClasificador> gestion;
    private Dialog dateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        this.setTitle(R.string.pagos);
        etFecha = (EditText) findViewById(R.id.etDate);
        etFecha.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        etFecha.setEnabled(false);
        etEmpleado = (EditText) findViewById(R.id.etEmpleado);
        etSaldo = (EditText) findViewById(R.id.etSaldo);
        tvSaldo = (TextView) findViewById(R.id.tvSaldo);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMonto = (EditText) findViewById(R.id.etMonto);
        spPeriodType = (Spinner) findViewById(R.id.spPeriodType);
        spMonthType = (Spinner) findViewById(R.id.spMonthType);
        btnSave = (ButtonRectangle) findViewById(R.id.btnSave);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnDate = (Button) findViewById(R.id.btnDate);
        tvSaldo.setVisibility(View.INVISIBLE);
        etSaldo.setVisibility(View.INVISIBLE);
        btnCancel = (ButtonRectangle) findViewById(R.id.btnCancel);
        actionForm = getIntent().getExtras().getInt("actionForm");
        loadDialogDate();
        if (actionForm ==1){
            loadSpinner();
            loadSearchEmpleados();
        }
        if (actionForm ==2){
            loadSpinner();
            gestionIdc= getIntent().getExtras().getLong("gestionIdc");
            periodoIdc= getIntent().getExtras().getLong("periodoIdc");
            empleado = (com.megasystem.suitepayment.entity.sale.Empleado) getIntent().getSerializableExtra("empleado");
            if(empleado.getId() != null){
                etEmpleado.setText(empleado.getNombre());
                btnSearch.setEnabled(false);
                DHistorialPagos dalHistorialPagos = new DHistorialPagos(Pagos.this,HistorialPagos.class);
                HistorialPagos objHistorialPago ;
                objHistorialPago = dalHistorialPagos.getByEmpleadoAndPeriod(empleado.getId(),gestionIdc,periodoIdc);
                if (objHistorialPago.getId() != null){
                    etSaldo.setText(Util.formatDouble(objHistorialPago.getSaldo()));
                    etSaldo.setEnabled(false);
                }
            }else{
                loadSpinner();
                loadSearchEmpleados();
            }




        }
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionForm ==1){
                    DPago dalPago = new DPago(Pagos.this,Pagos.class);
                    Pago pago = new Pago();
                    pago.setFecha(new Date());
                    pago.setEmpleadoId(empleado.getId());
                    pago.setGestionIdc(gestion.get(spPeriodType.getSelectedItemPosition()).getId());
                    pago.setPeriodoIdc(periodo.get(spMonthType.getSelectedItemPosition()).getId());
                    pago.setMonto(Double.valueOf(etMonto.getText().toString()));
                    pago.setDescripcion(etDescripcion.getText().toString());
                    pago.setEstado(1);
                    // pago.setDescripcion(e);
                    pago.setAction(Action.Insert);
                    DHistorialPagos dalHistorialPagos = new DHistorialPagos(Pagos.this,HistorialPagos.class);
                    HistorialPagos objHistorialPago ;
                    objHistorialPago = dalHistorialPagos.getByEmpleadoAndPeriod(empleado.getId(),gestion.get(spPeriodType.getSelectedItemPosition()).getId(),periodo.get(spMonthType.getSelectedItemPosition()).getId());
                    // List<HistorialPagos> lstHisto = dalHistorialPagos.list();
                    if(objHistorialPago.getId() != null) {
                        if (objHistorialPago.getSaldo()>= pago.getMonto()){
                            dalPago.save(pago);
                            objHistorialPago.setPagado(pago.getMonto()+objHistorialPago.getPagado());
                            objHistorialPago.setSaldo(objHistorialPago.getSaldo()-pago.getMonto());
                            objHistorialPago.setAction(Action.Update);
                            dalHistorialPagos.save(objHistorialPago);
                            Toast.makeText(Pagos.this,"Pago registrado Correctamente para " + empleado.getNombre(),Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }else{
                            Toast.makeText(Pagos.this,"No se puede pagar mas del saldo.Intente nuevamente.!",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(Pagos.this,"No se pudo procesar el pago, el empleado no cuenta con un registro de pago para este mes",Toast.LENGTH_LONG).show();


                    }

                }
                if (actionForm == 2){
                    DPago dalPago = new DPago(Pagos.this,Pagos.class);
                    Pago pago = new Pago();
                    pago.setFecha(new Date());
                    pago.setEmpleadoId(empleado.getId());
                    pago.setGestionIdc(gestion.get(spPeriodType.getSelectedItemPosition()).getId());
                    pago.setPeriodoIdc(periodo.get(spMonthType.getSelectedItemPosition()).getId());
                    pago.setMonto(Double.valueOf(etMonto.getText().toString()));
                    pago.setDescripcion(etDescripcion.getText().toString());
                    pago.setEstado(1);
                    // pago.setDescripcion(e);
                    pago.setAction(Action.Insert);
                    DHistorialPagos dalHistorialPagos = new DHistorialPagos(Pagos.this,HistorialPagos.class);
                    HistorialPagos objHistorialPago ;
                    objHistorialPago = dalHistorialPagos.getByEmpleadoAndPeriod(empleado.getId(),gestion.get(spPeriodType.getSelectedItemPosition()).getId(),periodo.get(spMonthType.getSelectedItemPosition()).getId());
                    // List<HistorialPagos> lstHisto = dalHistorialPagos.list();
                    if(objHistorialPago.getId() != null) {
                        if (objHistorialPago.getSaldo()>= pago.getMonto()){
                            dalPago.save(pago);
                            objHistorialPago.setPagado(pago.getMonto()+objHistorialPago.getPagado());
                            objHistorialPago.setSaldo(objHistorialPago.getSaldo()-pago.getMonto());
                            objHistorialPago.setAction(Action.Update);
                            dalHistorialPagos.save(objHistorialPago);
                            Toast.makeText(Pagos.this,"Pago registrado Correctamente para " + empleado.getNombre(),Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }else{
                            Toast.makeText(Pagos.this,"No se puede pagar mas del saldo.Intente nuevamente.!",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(Pagos.this,"No se pudo procesar el pago, el empleado no cuenta con un registro de pago para este mes",Toast.LENGTH_LONG).show();


                    }
                }
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
                dialogEmpleado.show();
            }
        });
    }
    private void loadDialogDate(){
        dateDialog = new Dialog(Pagos.this);
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
        etEmpleado.setText("");
        etMonto.setText("");

    }
    private void loadSearchEmpleados() {
        dialogEmpleado = new Dialog(Pagos.this);
        dialogEmpleado.setContentView(R.layout.search_empleado);
        dialogEmpleado.setTitle(getString(R.string.empleados));

        final EditText edtSearchP = (EditText) dialogEmpleado.findViewById(R.id.edtSearchP);
        loadProducts( edtSearchP.getText().toString().trim());
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
        List<Empleado> lstProducts = null;
        if (filter.length()==0 ){
            lstProducts = data.list();
        }else{
            lstProducts = data.listBy( filter, new String[]{});
        }
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
            DHistorialPagos dalHistorial = new DHistorialPagos(Pagos.this,HistorialPagos.class);
            final HistorialPagos objHistorialPago = dalHistorial.getByEmpleadoAndPeriod(data.get(position).getId(),gestion.get(spPeriodType.getSelectedItemPosition()).getId(),periodo.get(spMonthType.getSelectedItemPosition()).getId());
            activity.registerForContextMenu(view);

            TextView txtName = (TextView) view.findViewById(R.id.empName);
            txtName.setText(data.get(position).getNombre());

            TextView txtSaldo = (TextView) view.findViewById(R.id.empSalary);
            if (objHistorialPago.getId() != null){
                txtSaldo.setText(Util.formatDouble(objHistorialPago.getSaldo()));
            }else{
                txtSaldo.setText(Util.formatDouble(0D));
            }



            view.setTag(R.id.add, data.get(position));

            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        empleado = (Empleado) v.getTag(R.id.add);

                        searchDialogEmpleado.dismiss();
                        etEmpleado.setText(empleado.getNombre());
                        tvSaldo.setVisibility(View.VISIBLE);
                        etSaldo.setVisibility(View.VISIBLE);
                        etSaldo.setEnabled(false);
                        etSaldo.setText(Util.formatDouble(objHistorialPago.getSaldo()));
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
        if(actionForm == 1){
            DPsClasificador classifiers = new DPsClasificador(Pagos.this, PsClasificador.class);
            periodo = classifiers.list(EnumClasificadores.Periodo.getValor());
            gestion = classifiers.list(EnumClasificadores.Gestion.getValor());
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
            ArrayAdapter<String> sPeriodAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, periodoArray);
            sPeriodAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spMonthType.setAdapter(sPeriodAdapter);
            ArrayAdapter<String> sGestionAdapter = new ArrayAdapter<String>(this,  R.layout.spinner_item, gestionArray);
            sGestionAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spPeriodType.setAdapter(sGestionAdapter);
        }
        if (actionForm ==2){
            DPsClasificador classifiers = new DPsClasificador(Pagos.this, PsClasificador.class);
            periodo = classifiers.listById(periodoIdc);
            gestion = classifiers.listById(gestionIdc);
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
            ArrayAdapter<String> sPeriodAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, periodoArray);
            sPeriodAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spMonthType.setAdapter(sPeriodAdapter);
            ArrayAdapter<String> sGestionAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, gestionArray);
            sGestionAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spPeriodType.setAdapter(sGestionAdapter);
        }


    }
}
