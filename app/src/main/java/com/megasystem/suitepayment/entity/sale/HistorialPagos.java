package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dpedrazas on 11/24/2016.
 */
public class HistorialPagos extends Entity {

        private Date fecha;
        private Long empleadoId;
        private Long periodoIdc;
        private Long mesIdc;
        private Double pagar;
        private Double pagado;
        private Double saldo;

    public Long getPeriodoIdc() {
        return periodoIdc;
    }

    public void setPeriodoIdc(Long periodoIdc) {
        this.periodoIdc = periodoIdc;
    }

    public Long getMesIdc() {
        return mesIdc;
    }

    public void setMesIdc(Long mesIdc) {
        this.mesIdc = mesIdc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Double getPagar() {
        return pagar;
    }

    public void setPagar(Double pagar) {
        this.pagar = pagar;
    }

    public Double getPagado() {
        return pagado;
    }

    public void setPagado(Double pagado) {
        this.pagado = pagado;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}

