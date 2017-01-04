package com.megasystem.suitepayment;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.megasystem.suitepayment.service.Position;


public class Application extends android.app.Application {
    public final static String DataBaseName = "database6";
    public final static String tag = "Megasystem";
    public final static int locationUpdateTime = 50000;
    public final static int locationMinDistance = 20;
   // public static SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
   // public final static String webServices = "http://localhost:51300";
   // public final static String webServices = "http://192.168.0.201/mega";
    public final static String webServices = "http://192.168.1.2/mega";
    public final static String webServicesPublic = "http://200.58.181.5/mega";
    public final static String webServicesPrivate = "http://192.168.1.2/mega";
    public final static String formatDateShow = "dd-MM-yyyy HH:mm:ss";
    public final static String formatDateShort = "yyyy-MM-dd";
    public final static String webServiceUser = "";
    public final static String webServicePassword = "";
    public Location myLocation;
    private Intent serviceIntent;
    private static Application instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Application.tag, getString(R.string.app_name));
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
        Log.i(Application.tag, "Iniciando Servicio");
        serviceIntent = new Intent(this, Position.class);
        startService(serviceIntent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(serviceIntent);
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        Log.e(Application.tag, e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();

        }
        return instance;
    }
}
