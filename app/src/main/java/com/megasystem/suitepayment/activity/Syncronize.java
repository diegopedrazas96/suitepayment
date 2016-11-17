package com.megasystem.suitepayment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.service.Service;

import java.util.ArrayList;
import java.util.List;

public class Syncronize extends AppCompatActivity {
    private ListView list;
    private List<String> orders = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncronize);
        this.setTitle(R.string.app_name);
        TextView edtFooter = (TextView) findViewById(R.id.footer);
        orders.add("Sincronizar Envios");
        orders.add("Consolidar Envios");
        orders.add("Sincronizar Productos");
        String url = "https://www.facebook.com/megasystem.com.bo/";
        edtFooter.setText(Html.fromHtml("<a href=" + edtFooter.getText() + ">"+ url + "</a>"));
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(new Adapter(Syncronize.this,orders));
    }


    public class Adapter extends ArrayAdapter<String> {

        private final Context context;

        public Adapter(Context context, List<String> items) {
            super(context, R.layout.item_syncronize, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_syncronize, null);
            String obj = orders.get(position);
            ImageView title = (ImageView) view.findViewById(R.id.imvPicture);
            final Button btn = (Button) view.findViewById(R.id.details);
            title.setImageResource(R.mipmap.payments);
            btn.setText(obj);
            if(position == 1){
                title.setImageResource(R.mipmap.down);
            }
            if(position == 2){
                title.setImageResource(R.mipmap.sync);
            }
            btn.setTag(R.id.details, obj);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btn.getTag(R.id.details).toString().equals("Sincronizar Envios")) {
                        Call task = new Call(1,Syncronize.this);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    if (btn.getTag(R.id.details).toString().equals("Consolidar Envios")) {
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            Call task = new Call(2,Syncronize.this);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            Toast toas = Toast.makeText(Syncronize.this,"SIN CONEXION.",Toast.LENGTH_SHORT);

                            toas.show();
                        }

                    }
                    if (btn.getTag(R.id.details).toString().equals("Sincronizar Productos")) {
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            Call task = new Call(3,Syncronize.this);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            Toast toas = Toast.makeText(Syncronize.this,"SIN CONEXION.",Toast.LENGTH_SHORT);
                            toas.show();
                        }

                    }

                }
            });

            return view;
        }

    }

    private class Call extends AsyncTask<Integer, Void, Boolean> {

        private ProgressDialog progressDialog;
        private int action;
        private Context context;
        private int productRegistries;
        private int countSyncronize = 0;
        public Call(int varAction,Context cont) {
            this.action = varAction;
            this.context= cont;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Syncronize.this, null, getString(R.string.synchronizing));
        }

        @Override
        protected Boolean doInBackground(Integer... arg0) {
            try {
                Service service = new Service(Syncronize.this);
                //Llamada a las Dal
                if (action == 1){


                }
                if (action == 2){




                }
                if (action == 3){
                    //Obtiene los productos y los guarda




                }


                Log.i(Application.tag, "Sincronizacion Terminada");
                return true;
            } catch (Exception e) {

                Log.e(Application.tag, e.getMessage());
                return false;
            }

        }

        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                //Sincronizacion Terminada
                Log.i(Application.tag, "Actualizando pantalla principal");
                if (action ==1){
                    Toast toas = Toast.makeText(context,productRegistries + " Registros Obtenidos Correctamente.!",Toast.LENGTH_SHORT);
                    toas.show();
                }
                if (action ==2){
                    Toast toas = Toast.makeText(context,countSyncronize + " Envios Consolidados Correctamente.!",Toast.LENGTH_SHORT);
                    toas.show();
                }
                if (action ==3){
                    Toast toas = Toast.makeText(context,countSyncronize + " Productos Obtenidos Correctamente.!",Toast.LENGTH_SHORT);
                    toas.show();
                }
//                com.msfmegasystem.sales.fragment.Main fragment = (com.msfmegasystem.sales.fragment.Main) getFragmentManager().findFragmentById(R.id.container);
//                fragment.update();
            }
        }

    }

}
