package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.MsClasificador;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DMsClasificador extends Wrapper {

    @SuppressWarnings("unchecked")
    public DMsClasificador(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<MsClasificador> list() {
        return super.list("select * from msclasificador");
    }

    @SuppressWarnings("unchecked")
    public List<MsClasificador> list(String order) {
        return super.list("select * from msclasificador order by " + order);
    }

    public MsClasificador get() {
        return (MsClasificador) this.get("select * from msclasificador");
    }

    public MsClasificador getById(String idCliente) {
        return (MsClasificador) this.get("select * from msclasificador where IdCliente= '" + idCliente+ "'");
    }
}
