package com.megasystem.suitepayment.entity;

import android.util.Log;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.entity.annotation.Ignore;
import com.megasystem.suitepayment.entity.annotation.Key;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Entity implements Serializable, Cloneable {

    @Key
    protected Long Id;

    @Ignore
    private Action action = Action.None;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action value) {
        this.action = value;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getClone() {

        Entity obj = null;
        try {
            obj = (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e(Application.tag, e.getMessage());
        }
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getMe() {
        return (T) this;
    }
}