package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by usuario on 13/11/2016.
 */
public class Gasto extends Entity implements Serializable{

    private Long tipoIdc;
    private Date fecha;
    private String descripcion;
    private Double monto;
    private Long destinoIdc;
    @Ignore
    private PsClasificador TipoGasto;

    public PsClasificador getTipoGasto() {
        return TipoGasto;
    }

    public void setTipoGasto(PsClasificador tipoGasto) {
        TipoGasto = tipoGasto;
    }

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

    public Long getDestinoIdc() {
        return destinoIdc;
    }

    public void setDestinoIdc(Long destinoIdc) {
        this.destinoIdc = destinoIdc;
    }
}
