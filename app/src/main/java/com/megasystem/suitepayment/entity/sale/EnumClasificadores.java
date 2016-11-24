package com.megasystem.suitepayment.entity.sale;


public enum EnumClasificadores {
    Periodo(1), Gestion(2), Gasto(3);


    private final int valor;



    EnumClasificadores(int valor) {
        this.valor = valor;
    }
    public int getValor() { return valor; }
}
