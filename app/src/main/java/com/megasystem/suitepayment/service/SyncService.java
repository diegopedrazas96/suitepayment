package com.megasystem.suitepayment.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Toshiba on 28/02/2017.
 */
public class SyncService extends IntentService {


    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
//        try {
//            Timer pingTimer = new Timer();
//            pingTimer.scheduleAtFixedRate(new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    DEmpleado dalEmpleado = new DEmpleado(SyncService.this, Empleado.class);
//                    List<Empleado> lstEmpleados = dalEmpleado.listIncrement();
//                    for(Empleado objEmpleado : lstEmpleados){
//                        // Calendar cal = new GregorianCalendar(2010, Calendar.JULY, 1);
//                        Calendar gregorianCalendar = new GregorianCalendar();
//                        int days = gregorianCalendar.getActualMaximum(gregorianCalendar.DAY_OF_MONTH); // 28
//                        Double montoDiario = objEmpleado.getSalario()/days;
//                        objEmpleado.setMontoAcumulado(objEmpleado.getMontoAcumulado() + montoDiario);
//                        objEmpleado.setAction(Action.Update);
//                        dalEmpleado.save(objEmpleado);
//                    }
//
//                }
//            }, 0, 86400000);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
       // int interval = 1000*60*60*2; /* dos horas */
        try {
//            int interval = 5000; /* dos horas */
//            AlarmManager manager = (AlarmManager) SyncService.this.getSystemService(Context.ALARM_SERVICE);
//
//            Intent alarmIntent = new Intent(SyncService.this, MyReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(SyncService.this, 0, alarmIntent, 0);
//
//            manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pendingIntent);
            DEmpleado dalEmpleado = new DEmpleado(SyncService.this, Empleado.class);
                    List<Empleado> lstEmpleados = dalEmpleado.listIncrement();
                    for(Empleado objEmpleado : lstEmpleados){
                        // Calendar cal = new GregorianCalendar(2010, Calendar.JULY, 1);
                        Calendar gregorianCalendar = new GregorianCalendar();
                        int days = gregorianCalendar.getActualMaximum(gregorianCalendar.DAY_OF_MONTH); // 28
                        Double montoDiario = objEmpleado.getSalario()/days;
                        objEmpleado.setMontoAcumulado(objEmpleado.getMontoAcumulado() + montoDiario);
                        objEmpleado.setAction(Action.Update);
                        dalEmpleado.save(objEmpleado);
                        Log.i("Actualizar Sueldo", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) +" - " +  objEmpleado.getNombre() + " - Actualizar Sueldo");
                    }
        }catch (Exception e){
            Log.e("SYNSSERVICE",e.toString());
        }


    }

}
