package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.DGeneric;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPago;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.*;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class PrevioPagar extends AppCompatActivity {

    private List<PsClasificador> gestion;
    private ButtonRectangle btnSelect;
    private Dialog dialogGestion;
    private Long gestionIdc;
    private List<Map> lstPeriodos;
    private TextView total;
    private TextView tvGestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previo_pagar);
        tvGestion = (TextView) findViewById(R.id.tvGestion);
        total = (TextView) findViewById(R.id.total);
        btnSelect = (ButtonRectangle) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogGestion.show();
            }
        });
        loadDialog();


    }
    public void loadGrid(final Long gestionIdc) {
        final TableLayout table = (TableLayout) findViewById(R.id.table);
        LayoutInflater inflater = getLayoutInflater();
        double total = 0;
        TableRow row;
        table.removeAllViews();
        boolean sw = true;

        TextView txtName;
        TextView txtPagar;
        TextView txtPagado;
        TextView txtSaldo;
        DGeneric dalGeneric = new DGeneric(PrevioPagar.this);
        lstPeriodos = dalGeneric.getPeriodosSaldos(gestionIdc);


        for (final Map obj : lstPeriodos) {

            row = (TableRow) inflater.inflate(R.layout.item_pre_pagos, table, false);
            registerForContextMenu(row);
            row.setBackgroundColor((sw = !sw) ? Color.WHITE : Color.LTGRAY);



            txtName = (TextView) row.findViewById(R.id.tvMonth);
            txtName.setText(obj.get("descripcion").toString());

            txtPagar = (TextView) row.findViewById(R.id.tvPagar);
            txtPagar.setText(Util.formatDouble(Double.valueOf(obj.get("pagar").toString()) ));

            txtPagado = (TextView) row.findViewById(R.id.tvPagado);
            txtPagado.setText(Util.formatDouble(Double.valueOf(obj.get("pagado").toString()) ));

            txtSaldo = (TextView) row.findViewById(R.id.tvSaldo);
            txtSaldo.setText(Util.formatDouble(Double.valueOf(obj.get("saldo").toString())));
             row.setTag(R.id.add,Long.parseLong(obj.get("Id").toString()));
            // row.setTag(R.id.clientName, i);



            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.requestFocus();
                }
            });

            row.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(final View v) {
                    v.requestFocus();
                    new AlertDialog.Builder(PrevioPagar.this).setTitle(getString(R.string.app_name))
                            .setMessage(getString(R.string.question_payment))
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    Long periodoIdc = (Long) v.getTag(R.id.add);
                                    com.megasystem.suitepayment.entity.sale.Empleado empleado = new com.megasystem.suitepayment.entity.sale.Empleado();
                                    Intent intent = new Intent(PrevioPagar.this,Pagos.class);
                                    intent.putExtra("actionForm",2);
                                    intent.putExtra("gestionIdc",gestionIdc);
                                    intent.putExtra("periodoIdc",periodoIdc);
                                    intent.putExtra("empleado",empleado);
                                    startActivity(intent);

                                }
                            }).create().show();
                    return true;
                }
            });


            row.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.setBackgroundColor(Color.CYAN);
                    } else {
                         v.setBackgroundColor((Long.parseLong(v.getTag(R.id.add).toString()) % 2 == 0 ? Color.WHITE : Color.LTGRAY));
                    }
                }

            });
            total += Double.valueOf(obj.get("saldo").toString());
            table.addView(row);

        }
        this.total.setText(Util.formatDouble(total));
    }

    private void loadDialog() {
        dialogGestion = new Dialog(PrevioPagar.this);
        dialogGestion.setContentView(R.layout.search_empleado);
        dialogGestion.setTitle(getString(R.string.period));
        TextView tvText = (TextView) dialogGestion.findViewById(R.id.tvOverSearch);
        EditText edtSearchP = (EditText) dialogGestion.findViewById(R.id.edtSearchP);
        TextView txtNumber = (TextView) dialogGestion.findViewById(R.id.textNumber);
        edtSearchP.setVisibility(View.INVISIBLE);
        txtNumber.setVisibility(View.INVISIBLE);
        tvText.setText("Gestión");
        loadGestion();


    }
    private void loadGestion() {
        ListView list = (ListView) dialogGestion.findViewById(R.id.tableProducts);
        DGeneric bc = new DGeneric(getApplicationContext());
        List<Map> lstGestionSaldos = bc.getGestionSaldos();
        LazyAdapterProduct adapter = new LazyAdapterProduct(this, dialogGestion, lstGestionSaldos);
        list.setAdapter(adapter);
    }
    private class LazyAdapterProduct extends BaseAdapter {
        private Activity activity;
        private List<Map> data;
        private LayoutInflater inflater = null;
        private android.app.Dialog searchDialogEmpleado;

        public LazyAdapterProduct(Activity activity, android.app.Dialog searchProductDialog, List<Map> data) {
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

            Map obj = data.get(position);
            activity.registerForContextMenu(view);

            TextView txtName = (TextView) view.findViewById(R.id.empName);
            txtName.setText(obj.get("gestion").toString());

            TextView txtSaldo = (TextView) view.findViewById(R.id.empSalary);
            txtSaldo.setText(Util.formatDouble(Double.parseDouble(obj.get("saldo").toString())));


            Long gestiIdc = Long.parseLong(obj.get("Id").toString());

            view.setTag(R.id.agregar, gestiIdc);
            view.setTag(R.id.add, obj.get("gestion").toString());
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        gestionIdc = (Long) v.getTag(R.id.agregar);
                        String gestion = (String) v.getTag(R.id.add);
                        tvGestion.setText(getString(R.string.period)  + " - " + gestion);
                        loadGrid(gestionIdc);
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
}
