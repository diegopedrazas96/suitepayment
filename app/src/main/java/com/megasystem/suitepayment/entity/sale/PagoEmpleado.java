package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

import java.util.Date;

/**
 * Created by dpedrazas on 11/24/2016.
 */
public class PagoEmpleado extends Entity {

        private Date fecha;
        private Long empleadoId;
        private Double monto;
    private  String descripcion;
    private  Integer estado;
        @Ignore
        private Empleado empleado;

    public Date getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

