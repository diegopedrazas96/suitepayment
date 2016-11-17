package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Configuration extends AppCompatActivity {

    private Dialog dialog;
    private EditText txtMac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        dialog = new Dialog(Configuration.this);
        dialog.setContentView(R.layout.list_device);
        dialog.setTitle(getString(R.string.device_list));
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        this.setTitle(R.string.app_name);
        final SharedPreferences preferences = getSharedPreferences("Configurations", Context.MODE_PRIVATE);

        final EditText txtPrivate = (EditText) findViewById(R.id.privateService);
        txtPrivate.setText(preferences.getString("privateService", Application.webServicesPrivate));
        final EditText txtPublic = (EditText) findViewById(R.id.publicService);
        txtPublic.setText(preferences.getString("publicService", Application.webServicesPublic));
        final EditText txtUpdate = (EditText) findViewById(R.id.updateApp);
        txtUpdate.setText(preferences.getString("updateApp", "http://192.168.0.2/DownloadAPK/MSFMegasystem.apk"));
        txtMac = (EditText) findViewById(R.id.printerMAC);
        txtMac.setText(preferences.getString("printerMAC", "00:19:01:27:DD:13"));

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("privateService", txtPrivate.getText().toString());
                editor.putString("publicService", txtPublic.getText().toString());
                editor.putString("printerMAC", txtMac.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Configuracion actualizada correctamente!", Toast.LENGTH_SHORT).show();
                Configuration.this.finish();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration.this.finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDevice(dialog);
    }
    private void loadDevice( Dialog dialog) {
        ListView list = (ListView) dialog.findViewById(R.id.tableDevices);
        TextView txtNumber = (TextView) dialog.findViewById(R.id.textNumber);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        List<Item> listDevice = new ArrayList<Item>();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a
                // ListView
                listDevice.add(new Item(device.getName(), device.getAddress()));
            }
        }

        txtNumber.setText(pairedDevices.size() + " "
                + getString(R.string.registries));
        LazyAdapter adapter = new LazyAdapter(this, listDevice, dialog);
        list.setAdapter(adapter);
    }



    public class LazyAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater = null;
        private Dialog dialog;

        private List<Item> data = new ArrayList<Item>();

        public LazyAdapter(Activity a, List<Item> list, Dialog dial) {
            activity = a;
            data = list;
            dialog = dial;
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                view = inflater.inflate(R.layout.item_device, null);
            }

            if (position % 2 == 0) {
                view.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.list_selector_c));
            } else {
                view.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.list_selector));
            }

            registerForContextMenu(view);
            Log.i("ITem", "key: " + data.get(position).getKey() + " ,Value: " + data.get(position).getValue());

            TextView txtCode = (TextView) view.findViewById(R.id.name);
            txtCode.setText(data.get(position).getKey() == null ? "" : data
                    .get(position).getKey());


            TextView txtName = (TextView) view.findViewById(R.id.mac);
            txtName.setText(data.get(position).getValue());

            view.setTag(R.id.name, data.get(position));

            view.setOnTouchListener(new View.OnTouchListener() {


                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        txtMac.setText(((Item) v
                                .getTag(R.id.name)).getValue());
                        dialog.cancel();
                        txtMac.requestFocus();
                        getWindow()
                                .setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
    @SuppressWarnings("serial")
    public class Item implements Serializable {
        String key;
        String value;
        public Item(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }


    }

}
