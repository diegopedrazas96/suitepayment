package com.megasystem.suitepayment.data.sale;

import android.content.Context;
import com.megasystem.suitepayment.data.Wrapper;
import com.megasystem.suitepayment.entity.sale.Customer;

import java.util.List;

/**
 * Created by usuario on 15/07/2016.
 */
public class DUser extends Wrapper {

    public DUser(Context context) {
        super(context, Customer.class);
    }
    @SuppressWarnings("unchecked")
    public DUser(Context context, Class type) {
        super(context, type);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> list() {
        return super.list("select * from user");
    }

    @SuppressWarnings("unchecked")
    public List<Customer> list(String order) {
        return super.list("select * from user order by " + order);
    }

    public Customer get() {
        return (Customer) this.get("select * from user");
    }

}