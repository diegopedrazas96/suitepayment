package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;

/**
 * Created by usuario on 15/07/2016.
 */
public class User extends Entity {

    private String IdUsuario;
    private String IdSucursal;
    private String nombre;
    private String clave;
    private String nivel;
    private Long estado;


    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        IdSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}