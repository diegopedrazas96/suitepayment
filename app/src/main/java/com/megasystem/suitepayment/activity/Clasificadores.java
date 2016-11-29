package com.megasystem.suitepayment.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DMsClasificador;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.MsClasificador;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.util.List;

public class Clasificadores extends AppCompatActivity {
    private ListView lvMsClasifier ;
    private ListView lvPsClasifier ;
    private List<MsClasificador> lstMsClasificador;
    private List<PsClasificador> lstPsClasificador;
    private ButtonFloat btnFloat ;
    private MsClasificador objMsClasificador;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificadores);
        lvMsClasifier = (ListView) findViewById(R.id.list);
        lvPsClasifier = (ListView) findViewById(R.id.list2);
        btnFloat = (ButtonFloat) findViewById(R.id.add);
        DMsClasificador dalMsClasificador = new DMsClasificador(this,MsClasificador.class);
        lstMsClasificador = dalMsClasificador.list();

        lvMsClasifier.setAdapter(new Adapter(Clasificadores.this, lstMsClasificador));
        lvMsClasifier.setOnItemClickListener(new DrawerItemClickListener());
        //lvMsClasifier.setAdapter();
        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(Clasificadores.this);
                dialog.setContentView(R.layout.dialog_clasificador);
                dialog.setTitle(getString(R.string.device_list));
                final EditText etGroup = (EditText) dialog.findViewById(R.id.etGroup);
                etGroup.setText(objMsClasificador.getDescripcion());
                final EditText etDescripcion = (EditText) dialog.findViewById(R.id.etDescripcion);
                ButtonRectangle btnSave = (ButtonRectangle) dialog.findViewById(R.id.btnSave);
                ButtonRectangle btnCancel = (ButtonRectangle) dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DPsClasificador dalClasificador = new DPsClasificador(Clasificadores.this,PsClasificador.class);
                        PsClasificador objClasificador = new PsClasificador();
                        objClasificador.setDescripcion(etDescripcion.getText().toString());
                        objClasificador.setMsclasificadorId(objMsClasificador.getId());
                        objClasificador.setAction(Action.Insert);
                        dalClasificador.save(objClasificador);
                        dialog.dismiss();
                        Toast.makeText(Clasificadores.this, "Guardado Correctamente.!",Toast.LENGTH_SHORT).show();
                        updateList();
                    }
                });
                dialog.show();
            }
        });
    }
    private void updateList(){

        MsClasificador item = objMsClasificador;
        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        List<PsClasificador> lstProduct =  dalProduct.listbyMsClasifier(item.getId());
        lstPsClasificador = lstProduct;
        lvPsClasifier.setAdapter(new Adapter2(this,lstProduct));
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {
        objMsClasificador = lstMsClasificador.get(position);
        if(objMsClasificador.getId()== 1){
            btnFloat.setVisibility(View.GONE);
        }else{
            btnFloat.setVisibility(View.VISIBLE);
        }

        MsClasificador item = lstMsClasificador.get(position);
        DPsClasificador dalProduct = new DPsClasificador(this,PsClasificador.class);
        List<PsClasificador> lstProduct =  dalProduct.listbyMsClasifier(item.getId());
        lstPsClasificador = lstProduct;
        lvPsClasifier.setAdapter(new Adapter2(this,lstProduct));

    }

    public class Adapter extends ArrayAdapter<MsClasificador> {

        private final Context context;

        public Adapter(Context context, List<MsClasificador> items) {
            super(context, R.layout.item_group, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_group, null);

            TextView txtName = (TextView) view.findViewById(R.id.text_image);
            MsClasificador obj = lstMsClasificador.get(position);
            txtName.setText(obj.getDescripcion());

            return view;
        }

    }
    public class Adapter2 extends ArrayAdapter<PsClasificador> {

        private final Context context;

        public Adapter2(Context context, List<PsClasificador> items) {
            super(context, R.layout.item_basic, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_basic, null);

            TextView txtName = (TextView) view.findViewById(R.id.name);
           PsClasificador obj = lstPsClasificador.get(position);
            txtName.setText(obj.getDescripcion());

            return view;
        }

    }
}
