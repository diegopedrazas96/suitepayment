package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Customer;

import java.util.List;

/**
 * Created by usuario on 15/07/2016.
 */
public class DCustomer extends Wrapper {


    @SuppressWarnings("unchecked")
    public DCustomer(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> list() {
        return super.list("select * from customer");
    }

    @SuppressWarnings("unchecked")
    public List<Customer> list(String order) {
        return super.list("select * from customer order by " + order);
    }

    public Customer get() {
        return (Customer) this.get("select * from customer");
    }

    public Customer getById(String idCliente) {
        return (Customer) this.get("select * from customer where IdCliente= '" + idCliente+ "'");
    }

}