package com.megasystem.suitepayment.entity.sale;

import android.support.annotation.Nullable;

import com.megasystem.suitepayment.entity.Entity;

/**
 * Created by usuario on 13/11/2016.
 */
public class Clientes extends Entity {

    private String nombre;
    private String direccion;
    @Nullable
    private String telefono;
    private int estado;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
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

}
