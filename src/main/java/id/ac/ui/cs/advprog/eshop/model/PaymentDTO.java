package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String orderId;
    private String method;
    private String voucherCode;
    private String bankName;
    private String referenceCode;
}
