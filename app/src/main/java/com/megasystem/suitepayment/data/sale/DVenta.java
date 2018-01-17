package com.megasystem.suitepayment.data.sale;

import android.content.Context;

import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Pago;
import com.megasystem.suitepayment.entity.sale.Venta;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DVenta extends Wrapper {

    @SuppressWarnings("unchecked")
    public DVenta(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Venta> list() {
        return super.list("select * from venta");
    }

    @SuppressWarnings("unchecked")
    public List<Venta> list(Long order) {
        return super.list("select * from venta where montosaldo > 0 and estado = 1 and clienteId =" + order);
    }
    public List<Venta> list(Long idCliente,Long gestionIdc,Long periodoIdc) {
        return super.list("select * from pago where empleadoId= " + idCliente + " and gestionIdc = "+ gestionIdc + " and periodoIdc = "+ periodoIdc);
    }
    public Venta get() {
        return (Venta) this.get("select * from venta");
    }

    public Venta getById(String idCliente) {
        return (Venta) this.get("select * from venta where clienteId= '" + idCliente+ "'");

    }
}
