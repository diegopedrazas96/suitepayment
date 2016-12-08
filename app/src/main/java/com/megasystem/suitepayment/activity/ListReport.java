package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.gc.materialdesign.views.ButtonFloat;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListReport extends AppCompatActivity {
    private ListView lvItems;
    private ButtonFloat btnadd;
    private List<String> lstOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_empleado);
        lvItems = (ListView) findViewById(R.id.listView);
        btnadd = (ButtonFloat) findViewById(R.id.add);
        btnadd.setVisibility(View.GONE);
        lstOptions = new ArrayList<String>();
        lstOptions.add("Generar Planilla");
        lstOptions.add("Reporte de Gastos");
        lstOptions.add("Historial de Pagos");
        lstOptions.add("Reporte de Pagos");
        lstOptions.add("Backup Base de Datos");
        lvItems.setAdapter(new Adapter(ListReport.this, lstOptions));
    }

    public class Adapter extends ArrayAdapter<String> {

        private final Context context;

        public Adapter(Context context, List<String> items) {
            super(context, R.layout.item_empleado, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_basic, null);
            String obj = lstOptions.get(position);
            final TextView nombre = (TextView) view.findViewById(R.id.name);
            nombre.setText(obj);
            nombre.setTag(R.id.details, obj);
            nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String emp = (String) nombre.getTag(R.id.details);
                    if (emp.equals("Reporte de Gastos")) {
                        intent = new Intent(ListReport.this,ReporteGastos.class);
                        startActivity(intent);
                    }
                    if (emp.equals("Generar Planilla")) {
                        intent = new Intent(ListReport.this,ListGenerateSalary.class);
                        startActivity(intent);
                    }
                    if (emp.equals("Historial de Pagos")) {
                        intent = new Intent(ListReport.this,ReporteHistorialPagos.class);
                        startActivity(intent);
                    }
                    if (emp.equals("Reporte de Pagos")) {
                        intent = new Intent(ListReport.this,ReportePagos.class);
                        startActivity(intent);
                    }
                    if(emp.equals("Backup Base de Datos")){
                        backupBase();
                    }
                }
            });
            return view;
        }

    }
    public void backupBase(){
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File path = new File(sd, "/DCIM");
                path.mkdirs();
                String currentDBPath = "//data//" + getApplicationContext().getPackageName() + "//databases//"+ Application.DataBaseName +".db";
                String backupDBPath = "/DCIM/"+ Application.DataBaseName + new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()) + ".db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(ListReport.this, getString(R.string.message_backup_finish), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ListReport.this, getString(R.string.message_no_backup), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ListReport.this, getString(R.string.message_no_backup), Toast.LENGTH_LONG).show();
        }
    }
}
