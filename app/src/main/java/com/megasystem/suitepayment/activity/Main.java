package com.megasystem.suitepayment.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.megasystem.suitepayment.entity.sale.Pago;

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
                        intent = new Intent(Main.this, Empleado.class);
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

            TextView name = (TextView) v.findViewById(R.id.textView1);

            Drawable img = getResources().getDrawable(mThumbIds[position]);
            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();

            Drawable drawable = new BitmapDrawable(getResources(),
                    Bitmap.createScaledBitmap(bitmap, 64, 64, true));
            name.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);
            name.setText(mThumbLabels[position]);

            return v;
        }

        private Integer[] mThumbIds = { R.mipmap.clients, R.mipmap.invoice,
                R.mipmap.cart ,R.mipmap.payments, R.mipmap.options};
        private Integer[] mThumbLabels = { R.string.empleados, R.string.pagos,
                R.string.gastos,R.string.clasifier,R.string.configuracion };
    }
}
