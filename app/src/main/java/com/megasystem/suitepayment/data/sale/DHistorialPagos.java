package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.HistorialPagos;

import java.util.List;

/**
 * Created by dpedrazas on 11/30/2016.
 */
public class DHistorialPagos extends Wrapper {

    @SuppressWarnings("unchecked")
    public DHistorialPagos(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<HistorialPagos> list() {
        return super.list("select * from historialpagos");
    }
    public List<HistorialPagos> listByGestionPeriod(Long gestionIdc,Long periodoIdc) {
        return super.list("select * from historialpagos where  gestionIdc = "+ gestionIdc + " and periodoIdc = "+ periodoIdc);
    }

    @SuppressWarnings("unchecked")
    public List<HistorialPagos> list(String order) {
        return super.list("select * from historialpagos order by " + order);
    }

    public HistorialPagos get() {
        return (HistorialPagos) this.get("select * from historialpagos");
    }

    public HistorialPagos getById(int idCliente) {
        return (HistorialPagos) this.get("select * from historialpagos where empleadoId= " + idCliente);
    }
    public HistorialPagos getByEmpleadoAndPeriod(Long idCliente,Long gestionIdc,Long periodoIdc ) {
        return (HistorialPagos) this.get("select * from historialpagos where empleadoId= " + idCliente + " and gestionIdc = "+ gestionIdc + " and periodoIdc = "+ periodoIdc);
    }
}
