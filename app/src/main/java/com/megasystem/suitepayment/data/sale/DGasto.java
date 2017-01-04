package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Gasto;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DGasto extends Wrapper {

    @SuppressWarnings("unchecked")
    public DGasto(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Gasto> list() {
        return super.list("select * from gasto");
    }
    public List<Gasto> listByMonthPeriod(String fecha1,String fecha2) {
        return super.list("select * from gasto where fecha between '"+fecha1+" 00:00:00' and  '"+fecha2+" 23:59:59'");
        //return super.list("select * from gasto where fecha > date('"+fecha1+"')");
    }

    @SuppressWarnings("unchecked")
    public List<Gasto> list(String order) {
        return super.list("select * from gasto order by " + order);
    }

    public Gasto get() {
        return (Gasto) this.get("select * from gasto");
    }

    public Gasto getById(String idCliente) {
        return (Gasto) this.get("select * from gasto where IdCliente= '" + idCliente+ "'");
    }
}
