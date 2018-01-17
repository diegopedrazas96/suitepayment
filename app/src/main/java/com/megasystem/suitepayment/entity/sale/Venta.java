package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.activity.Cliente;
import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

import java.util.Date;

/**
 * Created by usuario on 13/11/2016.
 */
public class Venta extends Entity{
    private Long clienteId;
    private Date fecha;
    private String descripcion;
    private Double montoVenta;
    private Double montoPagado;
    private Double montoSaldo;
    private Integer estado;
    @Ignore
    private Cliente cliente;
    @Ignore
    private PsClasificador gestion;
    @Ignore
    private PsClasificador periodo;

    public Double getMontoVenta() {
        return montoVenta;
    }

    public void setMontoVenta(Double montoVenta) {
        this.montoVenta = montoVenta;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Double getMontoSaldo() {
        return montoSaldo;
    }

    public void setMontoSaldo(Double montoSaldo) {
        this.montoSaldo = montoSaldo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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


}
