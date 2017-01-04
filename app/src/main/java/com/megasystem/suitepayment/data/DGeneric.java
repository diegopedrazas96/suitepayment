package com.megasystem.suitepayment.data;

import android.content.Context;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes")
public class DGeneric extends Wrapper {

    @SuppressWarnings("unchecked")
    public DGeneric(Context context, Class type) {
        super(context, type);
    }
    public DGeneric(Context context) {
        super(context);
    }


    public List<Map> getGestionSaldos() {
        List<Map> lstResult = new ArrayList<Map>();
        String strQuery = " select g.id ,g.descripcion as gestion,sum(h.saldo) as saldo FROM psclasificador g " +
                "inner join historialpagos h on h.gestionIdc = g.id "  +
                "WHERE g.msclasificadorId = 2 " +
                " GROUP BY g.id,g.descripcion";
        try {
            lstResult = this.loadGenerigMap(strQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstResult;
    }
    public List<Map> getPeriodosSaldos(Long gestionIdc) {
        List<Map> lstResult = new ArrayList<Map>();
        String strQuery = " select p.id,p.descripcion,sum(h.pagar) as pagar,sum(h.pagado) as pagado,sum(saldo) as saldo from psclasificador p " +
                "inner join historialpagos h on h.periodoIdc = p.Id "  +
                "where p.msclasificadorId =1 and gestionIdc = " + gestionIdc +
                "  GROUP BY p.id,p.descripcion";
        try {
            lstResult = this.loadGenerigMap(strQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstResult;
    }


   /* public List<Map> getSalesResume(long type_idc) {
        List<Map> lstResult = new ArrayList<Map>();
        String strQuery = "SELECT * FROM(" +
                "SELECT sale.paymentType_idc as type, sale.invoiceNumber as invoiceNumber, customer.code as code,sum(detail.discount) as discount, sum(detail.partialTotal) as partialTotal FROM sale_saledetail detail " +
                "INNER JOIN sale_sale sale on (sale.Id=detail.sale_id and sale.type_idc = "+ type_idc +" and sale.status_idc="+ Enumerators.SaleStatus.Enable +") " +
                "INNER JOIN sale_customer customer on (customer.id=sale.customerLocal_id) " +
                "GROUP BY sale.invoiceNumber,customer.code " +
                "UNION " +
                "SELECT sale.paymentType_idc as type, sale.invoiceNumber as invoiceNumber, 'T-'||customer.id as code,sum(detail.discount) as discount, " +
                "sum(detail.partialTotal) as partialTotal FROM sale_saledetail detail " +
                "INNER JOIN sale_sale sale on (sale.Id=detail.sale_id and sale.type_idc = "+ type_idc +" and sale.status_idc="+ Enumerators.SaleStatus.Enable +") " +
                "INNER JOIN sale_templatecustomer customer on (customer.id=sale.templatecustomer_id)  " +
                "GROUP BY sale.invoiceNumber,customer.id) ORDER BY  invoiceNumber";
        try {
            lstResult = this.loadGenerigMap(strQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstResult;
    }*/



    /*public List<Map> getDisabled(long type_idc) {
        List<Map> lstResult = new ArrayList<Map>();
        String strQuery = "SELECT * FROM(" +
                "SELECT sale.paymentType_idc as type, sale.invoiceNumber as invoiceNumber, customer.code as code,sum(detail.discount) as discount, sum(detail.partialTotal) as partialTotal FROM sale_saledetail detail " +
                "INNER JOIN sale_sale sale on (sale.Id=detail.sale_id and sale.type_idc = "+ type_idc +" and sale.status_idc="+ Enumerators.SaleStatus.Disable +") " +
                "INNER JOIN sale_customer customer on (customer.id=sale.customerLocal_id) " +
                "GROUP BY sale.invoiceNumber,customer.code " +
                "UNION " +
                "SELECT sale.paymentType_idc as type, sale.invoiceNumber as invoiceNumber, 'T-'||customer.id as code,sum(detail.discount) as discount, " +
                "sum(detail.partialTotal) as partialTotal FROM sale_saledetail detail " +
                "INNER JOIN sale_sale sale on (sale.Id=detail.sale_id and sale.type_idc = "+ type_idc +" and sale.status_idc="+ Enumerators.SaleStatus.Disable +") " +
                "INNER JOIN sale_templatecustomer customer on (customer.id=sale.templatecustomer_id)  " +
                "GROUP BY sale.invoiceNumber,customer.id) ORDER BY  invoiceNumber";
        try {
            lstResult = this.loadGenerigMap(strQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstResult;
    }*/


}
