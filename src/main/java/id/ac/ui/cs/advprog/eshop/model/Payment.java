package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    Order order;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.order = order;
        this.paymentData = paymentData;
        this.status = "WAITING_PAYMENT";

        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data must not be null or empty");
        }

        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method must not be null or empty");
        }

        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        if (method == PaymentMethod.VOUCHER.getValue() && !paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code must be provided for voucher payment");
        }

        if (method == PaymentMethod.BANK.getValue() && (!paymentData.containsKey("bankName") || !paymentData.containsKey("referenceCode"))) {
            throw new IllegalArgumentException("Bank name and reference code must be provided for bank payment");
        }
    }

    public Payment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        this(id, method, order, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        this.status = status;
    }
}
