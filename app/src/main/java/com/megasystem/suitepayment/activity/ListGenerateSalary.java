






















































































































































































































































































































































































































































































































































































































package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.data.sale.DHistorialPagos;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.entity.sale.EnumClasificadores;
import com.megasystem.suitepayment.entity.sale.HistorialPagos;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListGenerateSalary extends AppCompatActivity {
    private List<com.megasystem.suitepayment.entity.sale.Empleado> lstEmpleados;
    private List<com.megasystem.suitepayment.entity.sale.Empleado> lstSelectedEmpleados;
    private Spinner spPeriodType;
    private Spinner spMonthType;
    private ButtonRectangle btnGenerate;
    private ListView lvEmpleados;
    private   List<PsClasificador> periodo;
    private List<PsClasificador> gestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_generate_salary);
        spPeriodType = (Spinner) findViewById(R.id.spPeriodType);
        spMonthType = (Spinner) findViewById(R.id.spMonthType);
        lstSelectedEmpleados = new ArrayList<Empleado>();
        btnGenerate = (ButtonRectangle) findViewById(R.id.btnGenerate);
        DEmpleado dalEmpleado = new DEmpleado(ListGenerateSalary.this, com.megasystem.suitepayment.entity.sale.Empleado.class);
        lstEmpleados = dalEmpleado.list();
        lvEmpleados = (ListView) findViewById(R.id.listView);
        DPsClasificador classifiers = new DPsClasificador(ListGenerateSalary.this, PsClasificador.class);
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
        lvEmpleados.setAdapter(new Adapter(ListGenerateSalary.this,lstEmpleados));
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DHistorialPagos dalHistorialPagos = new DHistorialPagos(ListGenerateSalary.this,HistorialPagos.class);
                for (Empleado objEmpleado : lstEmpleados){                    HistorialPagos objHistorialPago ;
                    objHistorialPago = dalHistorialPagos.getByEmpleadoAndPeriod(objEmpleado.getId(),periodo.get(spMonthType.getSelectedItemPosition()).getId(),gestion.get(spPeriodType.getSelectedItemPosition()).getId());
                    if(objHistorialPago == null){
                        objHistorialPago = new HistorialPagos();
                        objHistorialPago.setAction(Action.Insert);
                        objHistorialPago.setFecha(new Date());
                        objHistorialPago.setEmpleadoId(objEmpleado.getId());
                        objHistorialPago.setPeriodoIdc(periodo.get(spMonthType.getSelectedItemPosition()).getId());
                        objHistorialPago.setGestionIdc(gestion.get(spPeriodType.getSelectedItemPosition()).getId());
                        objHistorialPago.setPagar(objEmpleado.getSalario());
                        objHistorialPago.setPagado(0D);
                        objHistorialPago.setSaldo(objEmpleado.getSalario());
                        dalHistorialPagos.save(objHistorialPago);
                    }else{
                        Toast.makeText(ListGenerateSalary.this,"Ya existe registro de planilla para el empleado - " + objEmpleado.getNombre() + " para este mes y gestion.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    public class Adapter extends ArrayAdapter<com.megasystem.suitepayment.entity.sale.Empleado> {

        private final Context context;

        public Adapter(Context context, List<com.megasystem.suitepayment.entity.sale.Empleado> items) {
            super(context, R.layout.item_empleado_checkbox, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_basic, null);
            com.megasystem.suitepayment.entity.sale.Empleado obj = lstEmpleados.get(position);
            TextView nombre = (TextView) view.findViewById(R.id.name);
            nombre.setText(obj.getNombre());


            return view;
        }

    }
}
