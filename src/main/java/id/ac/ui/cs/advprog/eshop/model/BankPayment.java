package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class BankPayment extends Payment {
    public BankPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
    }

    public BankPayment(String id, String method, Order order, Map<String, String> paymentData) {
    }
}
