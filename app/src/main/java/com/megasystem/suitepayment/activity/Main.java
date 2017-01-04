package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Menú Principal");
        GridView gridview = (GridView) findViewById(R.id.gridView1);
        gridview.setAdapter(new ImageAdapter());

        /**
         * Seleccion de Actividades
         */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {


                Intent intent;
                switch (arg2) {

                    case 0:
                        intent = new Intent(Main.this, ListEmpleado.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Main.this, PrevioPagar.class);
                        startActivity(intent);

                    //    intent = new Intent(Main.this, Pagos.class);
                      //  intent.putExtra("actionForm",1);
                      //  startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Main.this, Gastos.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(Main.this, Clasificadores.class);
                        startActivity(intent);
                        break;
                    case 4:
                        //REPORTE
                        intent = new Intent(Main.this, ListReport.class);
                        startActivity(intent);
                        break;
                    case 5:
                        new AlertDialog.Builder(Main.this).setTitle(getString(R.string.message))
                                .setMessage(getString(R.string.question_backup))
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        backupBase();
                                    }
                                }).create().show();

                }


            }
        });

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
                Toast.makeText(Main.this, getString(R.string.message_backup_finish), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Main.this, getString(R.string.message_no_backup), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Main.this, getString(R.string.message_no_backup), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.question_exit))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Main.super.onBackPressed();
                    }
                }).create().show();
    }

    public class ImageAdapter extends BaseAdapter {

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.main_item, null);
            }
            Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
            TextView image = (TextView) v.findViewById(R.id.tvImage);
            TextView text = (TextView) v.findViewById(R.id.tvText);
            image.setTypeface(font);
            text.setTypeface(font);
            image.setText(mThumbIds[position]);
            text.setText(mThumbLabels[position]);

//            Drawable img = getResources().getDrawable(mThumbIds[position]);
//            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
//
//            Drawable drawable = new BitmapDrawable(getResources(),
//                    Bitmap.createScaledBitmap(bitmap, 64, 64, true));
            //name.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,null);


            return v;
        }

        private Integer[] mThumbIds = { R.string.icon_empleado3, R.string.icon_pagos,
                R.string.icon_gastos,R.string.icon_clasificadores,R.string.icon_reporte,R.string.icon_configuracion};
        private Integer[] mThumbLabels = { R.string.empleados, R.string.pagos,
                R.string.gastos,R.string.clasifier,R.string.menu_report,R.string.backup_base };
    }
}
