package com.megasystem.suitepayment.entity.common;

import java.io.Serializable;


public class Menu implements Serializable {

    private int id;
    private int label;

    public Menu(int id, int label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
