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
        return super.list("select * from "+tableName +"");
    }

    @SuppressWarnings("unchecked")
    public List<MsClasificador> list(String order) {
        return super.list("select * from "+tableName +" order by " + order);
    }

    public MsClasificador get() {
        return (MsClasificador) this.get("select * from "+tableName +"");
    }

    public MsClasificador getById(Long id) {
        return (MsClasificador) this.get("select * from "+tableName +" where Id= " + id+ "");
    }
}
