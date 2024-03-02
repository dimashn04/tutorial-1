package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class VoucherPayment extends Payment {

    public VoucherPayment(String id, String method, Order order, Map<String, String> paymentData) {
    }

    public VoucherPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
    }
    
}
