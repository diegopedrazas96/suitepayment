package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;

import java.util.Date;

/**
 * Created by usuario on 13/11/2016.
 */
public class Gasto extends Entity {
    private Long tipoIdc;
    private Date fecha;
    private String descripcion;
    private Double monto;

    public Long getTipoIdc() {
        return tipoIdc;
    }

    public void setTipoIdc(Long tipoIdc) {
        this.tipoIdc = tipoIdc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
