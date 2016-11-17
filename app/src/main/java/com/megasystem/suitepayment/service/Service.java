package com.megasystem.suitepayment.service;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.megasystem.suitepayment.entity.sale.*;

import java.util.ArrayList;
import java.util.List;


public class Service extends Web {

    public Service(Context context) {
        super(context);
    }

   /* public List<Parameter> getParameters() throws Exception {
        return super.getList("/api/parameter", new TypeToken<ArrayList<Parameter>>() {
        }.getType());
    }

    public List<ClassifierType> getClassifiers() throws Exception {
        return super.getList("/api/classifier", new TypeToken<ArrayList<ClassifierType>>() {
        }.getType());
    }

    public ClassifierType getClassifier(long id) throws Exception {
        return super.get("/api/classifier/" + id, ClassifierType.class);
    }

    public List<PriceList> getPriceLists() throws Exception {
        return super.getList("/api/pricelist", new TypeToken<ArrayList<PriceList>>() {
        }.getType());
    }*/


    public List<Customer> getClienteFood(String user) throws Exception {
        return super.getList("/api/customer/" + user, new TypeToken<ArrayList<Customer>>() {
        }.getType());
    }



//    publicList<Orders> postOrder(Orders order) throws Exception {
//        return super.post("/api/ordermobile", Orders.class, order);
//    }
    public User getUser(String imei) throws Exception {
        return super.get("/api/user/" + imei, User.class);
    }


    /*public Customer getCustomer(long id) throws Exception {
        return super.get("/api/customer/" + id, Customer.class);
    }

    public Client getClient(long id) throws Exception {
        return super.get("/api/client/" + id, Client.class);
    }


    public long login(Client obj) throws Exception {
        return super.post("/api/client/login/0", Client.class, obj);
    }*/
}

