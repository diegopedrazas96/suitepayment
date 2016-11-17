package com.megasystem.suitepayment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.megasystem.suitepayment.R;

//import com.cascada.app.data.app.DClient;
//import com.cascada.app.data.base.DClassifier;
//import com.cascada.app.data.base.DClassifierType;
//import com.cascada.app.data.base.DParameter;
//import com.cascada.app.entity.app.Client;
//import com.cascada.app.entity.base.Classifier;
//import com.cascada.app.entity.base.ClassifierType;
//import com.cascada.app.entity.base.Parameter;


public class ViewCustomer extends AppCompatActivity {
    private EditText custName;
    private EditText custCorporateName;
    private EditText custNit;
    private EditText custAddress;
    private EditText custBetween;
    private EditText custReference;
    private EditText custPhone;
    private EditText custCellPhone;
    private EditText custLatitud;
    private EditText custLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
         custName = (EditText) findViewById(R.id.custETName);
         custCorporateName= (EditText) findViewById(R.id.custETCorporateName);
         custNit= (EditText) findViewById(R.id.custETNit);
        custAddress = (EditText) findViewById(R.id.custETAddress);
         custBetween = (EditText) findViewById(R.id.custETBetween);
         custReference = (EditText) findViewById(R.id.custETReference);
        custPhone = (EditText) findViewById(R.id.custETPhone);
         custCellPhone = (EditText) findViewById(R.id.custETPhoneMobile);
        custLatitud = (EditText) findViewById(R.id.custEDLat);
         custLongitud = (EditText) findViewById(R.id.custEDLon);

        /*customer = (Orders) getIntent().getExtras().getSerializable("customer");

        custName.setText(customer.getCustomer().getNombre());
        custCorporateName.setText(customer.getDelivery().getNombre());
        custNit.setText(customer.getDelivery().getNit());
        custAddress.setText(customer.getCustomer().getDireccion());
        custBetween.setText(customer.getCustomer().getEntre());
        custReference.setText(customer.getCustomer().getReferencia());
        custPhone.setText(customer.getCustomer().getTelefono1());
        custCellPhone.setText(customer.getCustomer().getTelefono2());
        custLatitud.setText(customer.getCustomer().getLatitud());
        custLongitud.setText(customer.getCustomer().getLongitud());*/
    }



}
