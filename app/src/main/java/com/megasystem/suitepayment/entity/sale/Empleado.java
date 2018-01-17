package com.megasystem.suitepayment.entity.sale;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;

/**
 * Created by usuario on 13/11/2016.
 */
public class Empleado extends Entity {

    private String nombre;
    private String direccion;
    @Nullable
    private String telefono;
    private Double salario;
    @Nullable
    private Double montoAcumulado;
    private Integer estado;
    private Long tipoIdc;
    @Ignore
    private PsClasificador tipo;


    public PsClasificador getTipo() {
        return tipo;
    }

    public void setTipo(PsClasificador tipo) {
        this.tipo = tipo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getMontoAcumulado() {
        return montoAcumulado;
    }

    public void setMontoAcumulado(Double montoAcumulado) {
        this.montoAcumulado = montoAcumulado;
    }

    public Long getTipoIdc() {
        return tipoIdc;
    }

    public void setTipoIdc(Long tipoIdc) {
        this.tipoIdc = tipoIdc;
    }
}
