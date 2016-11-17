package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DEmpleado extends Wrapper {

    @SuppressWarnings("unchecked")
    public DEmpleado(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> list() {
        return super.list("select * from empleado");
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> list(String order) {
        return super.list("select * from empleado order by " + order);
    }

    public Empleado get() {
        return (Empleado) this.get("select * from empleado");
    }

    public Empleado getById(String idCliente) {
        return (Empleado) this.get("select * from empleado where IdCliente= '" + idCliente+ "'");
    }
}
