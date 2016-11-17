package com.megasystem.suitepayment.entity.common;

import java.io.Serializable;

/**
 * Created by usuario on 27/07/2016.
 */
public class Phone implements Serializable {

    private int id;
    private String label;

    public Phone(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}