package com.megasystem.suitepayment.data.sale;

import android.content.Context;

import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Clientes;
import com.megasystem.suitepayment.entity.sale.Empleado;

import java.util.List;

/**
 * Created by usuario on 13/11/2016.
 */
public class DCliente extends Wrapper {

    @SuppressWarnings("unchecked")

    public DCliente(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Clientes> list() {
        return super.list("select * from clientes");
    }

    public List<Clientes> list(Long periodoIdc, Long gestionIdc) {
        return super.list("select e.* from empleado e inner join historialpagos h on h.empleadoId = e.Id where h.periodoIdc =" +periodoIdc + " and h.gestionIdc=" + gestionIdc );
    }

    @SuppressWarnings("unchecked")
    public List<Clientes> list(String order) {
        return super.list("select * from clientes order by " + order);
    }

    public Clientes get() {
        return (Clientes) this.get("select * from clientes");
    }

    public Clientes getById(Long idCliente) {
        return (Clientes) this.get("select * from clientes where id= " + idCliente);
    }
    public List<Clientes> listBy( String filter,String [] relation) {
        //List list =null;
        //list = super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%')");

        //  loadRelations(list,relation);
        return super.list("SELECT * FROM clientes WHERE  nombre LIKE '%"+filter+"%'");
        //return list;
    }
    public List<Clientes> listBy( String filter,String [] relation,Long periodoIdc, Long gestionIdc) {
        //List list =null;
        //list = super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%')");

        //  loadRelations(list,relation);
        return super.list(" select e.* from clientes e inner join historialpagos h on h.empleadoId = e.Id where h.periodoIdc =" +periodoIdc + " and h.gestionIdc=" + gestionIdc + " and e.nombre LIKE '%"+filter+"%' ");


        //return list;
    }

}
