package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.PsClasificador;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DPsClasificador extends Wrapper {

    @SuppressWarnings("unchecked")
    public DPsClasificador(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<PsClasificador> list() {
        return super.list("select * from psclasificador");
    }

    @SuppressWarnings("unchecked")
    public List<PsClasificador> list(String order) {
        return super.list("select * from psclasificador order by " + order);
    }

    public PsClasificador get() {
        return (PsClasificador) this.get("select * from psclasificador");
    }

    public PsClasificador getById(String idCliente) {
        return (PsClasificador) this.get("select * from psclasificador where IdCliente= '" + idCliente+ "'");
    }
}
