package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

import java.util.Date;

/**
 * Created by usuario on 13/11/2016.
 */
public class Pago extends Entity{
    private Long empleadoId;
    private Date fecha;
    private String descripcion;
    private Long periodoIdc;
    private Long gestionIdc;
    private Double monto;
    private int estado;
    @Ignore
    private Empleado empleado;
    @Ignore
    private PsClasificador gestion;
    @Ignore
    private PsClasificador periodo;


    public Empleado getEmpleado() {
        return empleado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
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

    public Long getPeriodoIdc() {
        return periodoIdc;
    }

    public void setPeriodoIdc(Long periodoIdc) {
        this.periodoIdc = periodoIdc;
    }

    public Long getGestionIdc() {
        return gestionIdc;
    }

    public void setGestionIdc(Long gestionIdc) {
        this.gestionIdc = gestionIdc;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
