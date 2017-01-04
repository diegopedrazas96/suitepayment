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
    public List<PsClasificador> list(int clasificadorId) {
        return super.list("select * from psclasificador where msclasificadorid= " + clasificadorId);
    }
    public List<PsClasificador> listbyMsClasifier(Long id) {
        return super.list("select * from psclasificador where msclasificadorid= " + id);
    }

    public List<PsClasificador> listById(Long id) {
        return super.list("select * from psclasificador where id= " + id);
    }
    public PsClasificador get() {
        return (PsClasificador) this.get("select * from psclasificador");
    }

    public PsClasificador getById(Long id) {
        return (PsClasificador) this.get("select * from psclasificador where id= " + id );
    }
}
