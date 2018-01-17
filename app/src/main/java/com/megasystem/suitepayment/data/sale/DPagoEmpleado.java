package com.megasystem.suitepayment.data.sale;

import android.content.Context;

import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Pago;
import com.megasystem.suitepayment.entity.sale.PagoEmpleado;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DPagoEmpleado extends Wrapper {

    @SuppressWarnings("unchecked")
    public DPagoEmpleado(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<PagoEmpleado> list() {
        return super.list("select * from pagoempleado");
    }

    @SuppressWarnings("unchecked")
    public List<PagoEmpleado> listByEmpleado(Long order) {
        return super.list("select * from pagoempleado where estado=1 and empleadoId = " + order);
    }

    public PagoEmpleado get() {
        return (PagoEmpleado) this.get("select * from pagoempleado");
    }

    public PagoEmpleado getById(String idCliente) {
        return (PagoEmpleado) this.get("select * from pagoempleado where IdCliente= '" + idCliente+ "'");

    }
}
