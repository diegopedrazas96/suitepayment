package com.megasystem.suitepayment.entity.sale;


public enum EnumClasificadores {
    Periodo(1), Gestion(2), Gasto(3),Destino(4),Mensual(26),Temporal(27),PorObra(28);


    private final int valor;



    EnumClasificadores(int valor) {
        this.valor = valor;
    }
    public int getValor() { return valor; }
}
