package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;

/**
 * Created by usuario on 13/11/2016.
 */
public class Empleado extends Entity {

    private Long tipoIdc;
    private String nombre;
    private String direccion;
    private String telefono;
    private Double salario;



    public Long getTipoIdc() {
        return tipoIdc;
    }

    public void setTipoIdc(Long tipoIdc) {
        this.tipoIdc = tipoIdc;
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
}
