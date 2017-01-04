package com.megasystem.suitepayment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPago;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.HistorialPagos;
import com.megasystem.suitepayment.entity.sale.Pago;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportePagos extends AppCompatActivity {
    private List<Pago> lstPago;
    private TextView tvTotal;
    private TextView tvSaldo;
    private ButtonRectangle btnSearch;
   private com.megasystem.suitepayment.entity.sale.Empleado empleado;
    private Long gestionIdc;
    private Long periodoIdc;
    private TextView tvEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_pagos);
        this.setTitle(R.string.title_payment_report);
        tvTotal = (TextView) findViewById(R.id.total);
        tvEmpleado = (TextView) findViewById(R.id.tvEmpleado);
        tvSaldo = (TextView) findViewById(R.id.tvSaldo);
        gestionIdc= getIntent().getExtras().getLong("gestionIdc");
        periodoIdc= getIntent().getExtras().getLong("periodoIdc");
        empleado = (com.megasystem.suitepayment.entity.sale.Empleado) getIntent().getSerializableExtra("empleado");
        btnSearch = (ButtonRectangle)findViewById(R.id.btnSearch);
        tvEmpleado.setText(empleado.getNombre());
        DHistorialPagos dalHistorialPagos = new DHistorialPagos(ReportePagos.this,HistorialPagos.class);
        HistorialPagos objHistorialPago ;
        objHistorialPago = dalHistorialPagos.getByEmpleadoAndPeriod(empleado.getId(),gestionIdc,periodoIdc);
        if (objHistorialPago.getId() != null){
            tvSaldo.setText(Util.formatDouble(objHistorialPago.getSaldo()));

        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportePagos.this,Pagos.class);
                intent.putExtra("gestionIdc",gestionIdc);
                intent.putExtra("periodoIdc",periodoIdc);
                intent.putExtra("empleado",empleado);
                intent.putExtra("actionForm",2);
                startActivity(intent);


            }
        });
        loadGrid();
    }

    public void loadGrid() {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        LayoutInflater inflater = getLayoutInflater();
        double total = 0;
        TableRow row;
        table.removeAllViews();
        boolean sw = true;
        TextView txtCode;
        TextView txtName;
        TextView txtPrice;
        TextView txtQuantity;
        TextView txtSubtotal;
        DPago dalPagos = new DPago(ReportePagos.this, Pago.class);
        lstPago = dalPagos.list(empleado.getId(),gestionIdc,periodoIdc);
        List<Pago> lstnuew = dalPagos.list();
        loadObject();
        for (Pago obj : lstPago) {
            if ((obj.getAction().equals(Action.Delete))) {
                continue;
            }

            row = (TableRow) inflater.inflate(R.layout.item_reporte_pagos, table, false);
            registerForContextMenu(row);
            row.setBackgroundColor((sw = !sw) ? Color.WHITE : Color.LTGRAY);

            txtCode = (TextView) row.findViewById(R.id.tvFecha);
            String dateFormated = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha()).toString();
           // txtDate.setText(dateFormated);
            txtCode.setText(dateFormated);

            txtName = (TextView) row.findViewById(R.id.tvEmployeeName);
            txtName.setText(obj.getDescripcion());

            //txtPrice = (TextView) row.findViewById(R.id.tvTipo);
           // txtPrice.setText(obj.getTipoGasto().getDescripcion());

            txtQuantity = (TextView) row.findViewById(R.id.tvMonto);
            txtQuantity.setText(Util.formatDouble(obj.getMonto()));


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
            total += obj.getMonto();
            table.addView(row);

        }
        tvTotal.setText(Util.formatDouble(total));
    }

    private void loadObject() {
        for (Pago obj : lstPago){
            DPsClasificador dalClasificador = new DPsClasificador(ReportePagos.this, PsClasificador.class);
            obj.setPeriodo(dalClasificador.getById(obj.getPeriodoIdc()));
            obj.setGestion(dalClasificador.getById(obj.getGestionIdc()));
            DEmpleado dalEmpleado = new DEmpleado(ReportePagos.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
            obj.setEmpleado(dalEmpleado.getById(obj.getEmpleadoId()));;
        }
    }
}
