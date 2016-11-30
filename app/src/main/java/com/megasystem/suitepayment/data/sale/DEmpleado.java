package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Empleado;
import joquery.core.QueryException;

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

    public Empleado getById(Long idCliente) {
        return (Empleado) this.get("select * from empleado where id= " + idCliente);
    }
    public List<Empleado> listBy( String filter,String [] relation) {
        List list =null;
        list = super.list("SELECT * FROM empleado WHERE  nombre LIKE '%"+filter+"%')");

        //  loadRelations(list,relation);
        return list;
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
