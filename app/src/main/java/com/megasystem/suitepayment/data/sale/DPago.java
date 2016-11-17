package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Pago;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DPago extends Wrapper {

    @SuppressWarnings("unchecked")
    public DPago(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Pago> list() {
        return super.list("select * from pago");
    }

    @SuppressWarnings("unchecked")
    public List<Pago> list(String order) {
        return super.list("select * from pago order by " + order);
    }

    public Pago get() {
        return (Pago) this.get("select * from pago");
    }

    public Pago getById(String idCliente) {
        return (Pago) this.get("select * from pago where IdCliente= '" + idCliente+ "'");
    }
}
