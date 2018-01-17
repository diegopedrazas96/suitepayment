package com.megasystem.suitepayment.service;

import android.widget.Toast;

import com.megasystem.suitepayment.data.sale.DEmpleado;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Toshiba on 09/03/2017.
 */
public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "Tu lógica de negocio irá aquí. En caso de requerir más de unos milisegundos, debería de la tarea a un servicio", Toast.LENGTH_LONG).show();
        DEmpleado dalEmpleado = new DEmpleado(context, Empleado.class);
                    List<Empleado> lstEmpleados = dalEmpleado.listIncrement();
                    for(Empleado objEmpleado : lstEmpleados){
                        // Calendar cal = new GregorianCalendar(2010, Calendar.JULY, 1);
                        Calendar gregorianCalendar = new GregorianCalendar();
                        int days = gregorianCalendar.getActualMaximum(gregorianCalendar.DAY_OF_MONTH); // 28
                        Double montoDiario = objEmpleado.getSalario()/days;
                        objEmpleado.setMontoAcumulado(objEmpleado.getMontoAcumulado() + montoDiario);
                        objEmpleado.setAction(Action.Update);
                        dalEmpleado.save(objEmpleado);
                    }
    }
}