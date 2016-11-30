package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

import java.util.Date;

/**
 * Created by dpedrazas on 11/24/2016.
 */
public class HistorialPagos extends Entity {

        private Date fecha;
        private Long empleadoId;
        private Long gestionIdc;
        private Long periodoIdc;
        private Double pagar;
        private Double pagado;
        private Double saldo;
        @Ignore
        private Empleado empleado;
        @Ignore
        private PsClasificador gestion;
        @Ignore
        private PsClasificador periodo;

    public Long getGestionIdc() {
        return gestionIdc;
    }

    public void setGestionIdc(Long gestionIdc) {
        this.gestionIdc = gestionIdc;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public PsClasificador getGestion() {
        return gestion;
    }

    public void setGestion(PsClasificador gestion) {
        this.gestion = gestion;
    }

    public PsClasificador getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PsClasificador periodo) {
        this.periodo = periodo;
    }

    public Long getPeriodoIdc() {
        return periodoIdc;
    }

    public void setPeriodoIdc(Long periodoIdc) {
        this.periodoIdc = periodoIdc;
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

