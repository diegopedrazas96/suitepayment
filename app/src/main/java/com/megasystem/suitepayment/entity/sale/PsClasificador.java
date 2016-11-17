package com.megasystem.suitepayment.entity.sale;

import com.megasystem.suitepayment.entity.Entity;

/**
 * Created by usuario on 13/11/2016.
 */
public class PsClasificador extends Entity{
    private Long msclasificadorId;
    private String descripcion;
    private Long estado;

    public Long getMsclasificadorId() {
        return msclasificadorId;
    }

    public void setMsclasificadorId(Long msclasificadorId) {
        this.msclasificadorId = msclasificadorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}
