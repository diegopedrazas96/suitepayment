package com.megasystem.suitepayment.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DGasto;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Gasto;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReporteGastos extends AppCompatActivity {
    private List<Gasto> lstGasto ;
    private TextView tvTotal;
    private Button desdeDate;
    private Button hastaDate;
    private ButtonRectangle btnSearch;
    private Dialog dateDialog;
    private Dialog dateDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_gastos);
        this.setTitle(R.string.title_spend);
        tvTotal = (TextView) findViewById(R.id.total);
        desdeDate = (Button) findViewById(R.id.desdeDate);
        hastaDate = (Button) findViewById(R.id.hastaDate);
        loadDialogDate();
        loadDialogDate2();
        desdeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
        hastaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog2.show();
            }
        });
        btnSearch = (ButtonRectangle)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       String fecha1 = new SimpleDateFormat("yyyy/MM/dd 00:00").format(desdeDate.getText().toString()).toString();
          //      String fecha2 = new SimpleDateFormat("yyyy/MM/dd 23:59").format(hastaDate.getText().toString()).toString();
               loadGrid(desdeDate.getText().toString(),hastaDate.getText().toString());
               // loadGrid(fecha1,fecha2);
            }
        });

    }

    private void loadDialogDate(){
        dateDialog = new Dialog(ReporteGastos.this);
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
                String fecha1 = new SimpleDateFormat("yyyy-MM-dd").format(Util.getDateFromDatePicket(deliveryDate)).toString();
                desdeDate.setText(fecha1);
                dateDialog.dismiss();
            }
        });

    }
    private void loadDialogDate2(){
        dateDialog2 = new Dialog(ReporteGastos.this);
        dateDialog2.setContentView(R.layout.sale_date);
        dateDialog2.setTitle(getString(R.string.date_title));

        final DatePicker deliveryDate = (DatePicker) dateDialog2.findViewById(R.id.deliveryDate);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.HOUR, 24);

        deliveryDate.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        final ButtonRectangle btnCancelDate = (ButtonRectangle) dateDialog2.findViewById(R.id.btnCancel);
        btnCancelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog2.cancel();
            }
        });

        final ButtonRectangle btnSaveDate = (ButtonRectangle) dateDialog2.findViewById(R.id.btnSave);
        btnSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(Util.getDateFromDatePicket(deliveryDate)).toString();
                hastaDate.setText(fecha2);
                dateDialog2.dismiss();
               //objSale.setDeliveryDate(Util.getDateFromDatePicket(deliveryDate));
            }
        });

    }
    public void loadGrid(String fecha1,String fecha2) {
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
        DGasto dalGasto = new DGasto(ReporteGastos.this, Gasto.class);
        lstGasto = dalGasto.listByMonthPeriod(fecha1,fecha2);
        List<Gasto> lstnuew = dalGasto.list();
        loadObject();
        for (Gasto obj : lstGasto) {
            if ((obj.getAction().equals(Action.Delete))) {
                continue;
            }

            row = (TableRow) inflater.inflate(R.layout.item_reporte_gasto, table, false);
            registerForContextMenu(row);
            row.setBackgroundColor((sw = !sw) ? Color.WHITE : Color.LTGRAY);

            txtCode = (TextView) row.findViewById(R.id.tvFecha);
            String dateFormated = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha()).toString();
           // txtDate.setText(dateFormated);
            txtCode.setText(dateFormated);

            txtName = (TextView) row.findViewById(R.id.tvDescripcion);
            txtName.setText(obj.getDescripcion());

            txtPrice = (TextView) row.findViewById(R.id.tvTipo);
            txtPrice.setText(obj.getTipoGasto().getDescripcion());

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
        for (Gasto obj : lstGasto){
            DPsClasificador dalClasificador = new DPsClasificador(ReporteGastos.this, PsClasificador.class);
            obj.setTipoGasto(dalClasificador.getById(obj.getTipoIdc()));
        }
    }
}
