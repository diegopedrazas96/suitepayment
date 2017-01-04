package com.megasystem.suitepayment.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.R;
import com.megasystem.suitepayment.data.sale.DMsClasificador;
import com.megasystem.suitepayment.data.sale.DPsClasificador;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.MsClasificador;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.security.MessageDigest;
import java.util.List;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  getHashKey();
        DMsClasificador dalMsClasificador = new DMsClasificador(this, MsClasificador.class);
        MsClasificador msClasificador = new MsClasificador();
        List<MsClasificador> lstMsClasificador = dalMsClasificador.list();
       try {
           if (lstMsClasificador.size() == 0) {
               msClasificador.setDescripcion("Periodo");
               msClasificador.setAction(Action.Insert);
               dalMsClasificador.save(msClasificador);
               // LLENAR DATOS DE PERIODO
               DPsClasificador dalPsClasificador = new DPsClasificador(Splash.this, PsClasificador.class);
               PsClasificador objClasificador = new PsClasificador();
               objClasificador.setDescripcion("ENERO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);
               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("FEBRERO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("MARZO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("ABRIL");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("MAYO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("JUNIO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("JULIO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("AGOSTO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("SEPTIEMBRE");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("OCTUBRE");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("NOVIEMBRE");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("DICIEMBRE");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(1L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);
                //LLENAR DATOS DE GESTION
               msClasificador = new MsClasificador();
               msClasificador.setAction(Action.Insert);
               msClasificador.setDescripcion("Gestion");
               dalMsClasificador.save(msClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("2017");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(2L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

                // LLENAR DATOS DE GASTO
               msClasificador = new MsClasificador();
               msClasificador.setAction(Action.Insert);
               msClasificador.setDescripcion("Gasto");
               dalMsClasificador.save(msClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("VIVERES");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("COMBUSTIBLE");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("ALIMENTO DE GANADO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("REPUESTOS");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("ENVIO DE DINERO");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("VENTA DE GANADO");
               objClasificador.setEstado(2L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);

               objClasificador = new PsClasificador();
               objClasificador.setDescripcion("MATERIAL DE CONSTRUCCION");
               objClasificador.setEstado(1L);
               objClasificador.setMsclasificadorId(3L);
               objClasificador.setAction(Action.Insert);
               dalPsClasificador.save(objClasificador);



           } else {


           }
       }catch (Exception e){
           Log.e("Splash",e.toString());
       }


    }

    @Override
    public void onResume() {
        super.onResume();
        // Validamos google play services
        if (checkPlayServices()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);

                            startActivity(new Intent(Splash.this, Main.class));

//                            Intent intent = new Intent(Splash.this, Main.class);
//                            intent.putExtra("client", client);
//                            startActivity(intent);

                        finish();
                    } catch (Exception e) {
                        Log.e(Application.tag, e.getMessage());
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }

    @Override
    public void onBackPressed() {
        // evadimos el backkey para que no salga de la aplicacion
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(Application.tag, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
}
