package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Empleado;
import com.megasystem.suitepayment.service.MyReceiver;

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
        return super.list("select * from empleado where estado =1");
    }
    public List<Empleado> listIncrement() {
        return super.list("select * from empleado where estado =1 and tipoidc <> 28");
    }
    public List<Empleado> listAll() {
        return super.list("select * from empleado");
    }
    public List<Empleado> list(Long periodoIdc, Long gestionIdc) {
        return super.list("select e.* from empleado e inner join historialpagos h on h.empleadoId = e.Id where h.periodoIdc =" +periodoIdc + " and h.gestionIdc=" + gestionIdc );
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> list(String order) {
        return super.list("select * from empleado order by " + order);
    }

    public Empleado get() {
        return (Empleado) this.get("select * from empleado");
    }

    public Empleado getById(Long idCliente) {
        return (Empleado) this.get("select * from empleado where id= " + idCliente);
    }
    public List<Empleado> listBy( String filter,String [] relation) {
        //List list =null;
        //list = super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%')");

        //  loadRelations(list,relation);
        return super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%'");
        //return list;
    }
    public List<Empleado> listBy( String filter,String [] relation,Long periodoIdc, Long gestionIdc) {
        //List list =null;
        //list = super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%')");

        //  loadRelations(list,relation);
        return super.list(" select e.* from empleado e inner join historialpagos h on h.empleadoId = e.Id where h.periodoIdc =" +periodoIdc + " and h.gestionIdc=" + gestionIdc + " and e.nombre LIKE '%"+filter+"%' ");


        //return list;
    }
 /*   protected void loadRelations(List<Empleado> lstObj, String[] Relaciones) throws QueryException {
        if(Relaciones.length==0 ||lstObj==null|| lstObj.isEmpty()){
            return;
        }
        DEmpleado  dalProduct;
        List<Empleado> lstProduct = null;
        String Llaves ="";
        int i=0;
        for (String Clase : Relaciones) {
            if (Clase.equals(PriceListProduct.relation.Product.name())) {
                Relaciones[i]="";
                Llaves=this.extract(lstObj, "product_id");
                dalProduct = new DProduct(this.context);
                lstProduct = dalProduct.returnChildById(Llaves);
            }
            i++;
        }
        if (Relaciones.length > 0) {
            for ( PriceListProduct  Obj : lstObj) {
                if (lstProduct!=null && lstProduct.size() > 0) {
                    Obj.setProduct(CQ.filter(lstProduct).where().property("Id").eq().value(Obj.getProduct_id()).first());
                }
            }
        }
    }*/
}
