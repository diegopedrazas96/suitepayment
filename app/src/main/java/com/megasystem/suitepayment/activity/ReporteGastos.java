package com.megasystem.suitepayment.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DGasto;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Gasto;
import com.megasystem.suitepayment.entity.sale.PsClasificador;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReporteGastos extends AppCompatActivity {
    private List<Gasto> lstGasto ;
    private TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_gastos);
        tvTotal = (TextView) findViewById(R.id.total);

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
        DGasto dalGasto = new DGasto(ReporteGastos.this, Gasto.class);
        lstGasto = dalGasto.list();
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
