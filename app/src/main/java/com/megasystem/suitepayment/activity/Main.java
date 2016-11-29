package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.megasystem.suitepayment.R;

public class Main extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        intent = new Intent(Main.this, Pagos.class);
                        startActivity(intent);
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
                        intent = new Intent(Main.this, Configuration.class);
                        startActivity(intent);
                        break;
                }


            }
        });

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

        private Integer[] mThumbIds = { R.string.icon_empleado2, R.string.icon_pagos,
                R.string.icon_gastos,R.string.icon_clasificadores,R.string.icon_reporte,R.string.icon_configuracion};
        private Integer[] mThumbLabels = { R.string.empleados, R.string.pagos,
                R.string.gastos,R.string.clasifier,R.string.menu_report,R.string.configuracion };
    }
}
