package com.megasystem.suitepayment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReporteHistorialPagos extends AppCompatActivity {
    private List<HistorialPagos> lstPagos;
    private TextView tvTotal;
    private Spinner spPeriodType;
    private Spinner spMonthType;
    private   List<PsClasificador> periodo;
    private List<PsClasificador> gestion;
    private ButtonRectangle btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_historial_pagos);
        tvTotal = (TextView) findViewById(R.id.total);
        spPeriodType = (Spinner) findViewById(R.id.spPeriodType);
        spMonthType = (Spinner) findViewById(R.id.spMonthType);
        btnSearch = (ButtonRectangle)findViewById(R.id.btnSearch);
        loadSpinner();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGrid(gestion.get(spPeriodType.getSelectedItemPosition()).getId(),periodo.get(spMonthType.getSelectedItemPosition()).getId());
            }
        });

    }
    private void loadSpinner() {
        DPsClasificador classifiers = new DPsClasificador(ReporteHistorialPagos.this, PsClasificador.class);
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
        ArrayAdapter<String> sPeriodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periodoArray);
        sPeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonthType.setAdapter(sPeriodAdapter);
        ArrayAdapter<String> sGestionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gestionArray);
        sGestionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriodType.setAdapter(sGestionAdapter);
    }
    public void loadGrid(Long gestionIdc,Long periodoIdc) {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        LayoutInflater inflater = getLayoutInflater();
        double total = 0;
        TableRow row;
        table.removeAllViews();
        boolean sw = true;
        TextView txtCode;
        TextView txtName;
        TextView txtPagar;
        TextView txtPagado;
        TextView txtSaldo;
        DHistorialPagos dalHistorialPagos = new DHistorialPagos(ReporteHistorialPagos.this, HistorialPagos.class);
        lstPagos = dalHistorialPagos.listByGestionPeriod(gestionIdc,periodoIdc);

        loadObject();
        for (HistorialPagos obj : lstPagos) {
            if ((obj.getAction().equals(Action.Delete))) {
                continue;
            }

            row = (TableRow) inflater.inflate(R.layout.item_reporte_historial_pagos, table, false);
            registerForContextMenu(row);
            row.setBackgroundColor((sw = !sw) ? Color.WHITE : Color.LTGRAY);

            txtCode = (TextView) row.findViewById(R.id.empCode);
            String dateFormated = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha()).toString();
           // txtDate.setText(dateFormated);
            txtCode.setText(dateFormated);

            txtName = (TextView) row.findViewById(R.id.empName);
            txtName.setText(obj.getEmpleado().getNombre());

            txtPagar = (TextView) row.findViewById(R.id.pagar);
            txtPagar.setText(Util.formatDouble(obj.getPagar()));

            txtPagado = (TextView) row.findViewById(R.id.pagado);
            txtPagado.setText(Util.formatDouble(obj.getPagado()));

            txtSaldo = (TextView) row.findViewById(R.id.saldo);
            txtSaldo.setText(Util.formatDouble(obj.getSaldo()));
           // row.setTag(R.id.clientCode, obj);
           // row.setTag(R.id.clientName, i);



            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.requestFocus();
                }
            });

            row.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    v.requestFocus();
                    return true;
                }
            });


            row.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.setBackgroundColor(Color.CYAN);
                    } else {
                        // v.setBackgroundColor((Integer.parseInt(v.getTag(R.id.clientName).toString()) % 2 == 0 ? Color.WHITE : Color.LTGRAY));
                    }
                }

            });
            total += obj.getSaldo();
            table.addView(row);

        }
        tvTotal.setText(Util.formatDouble(total));
    }

    private void loadObject() {
        for (HistorialPagos obj : lstPagos){
            DPsClasificador dalClasificador = new DPsClasificador(ReporteHistorialPagos.this, PsClasificador.class);
            obj.setPeriodo(dalClasificador.getById(obj.getPeriodoIdc()));
            obj.setGestion(dalClasificador.getById(obj.getGestionIdc()));
            DEmpleado dalEmpleado = new DEmpleado(ReporteHistorialPagos.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
            obj.setEmpleado(dalEmpleado.getById(obj.getEmpleadoId()));;
        }
    }
}
