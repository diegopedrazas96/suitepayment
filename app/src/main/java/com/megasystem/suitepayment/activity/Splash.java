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

import java.security.MessageDigest;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  getHashKey();
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
