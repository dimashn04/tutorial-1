package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;
    private List<Product> products;

    @BeforeEach
    void setup() {
        this.paymentData = new HashMap<>();

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Product product2 = new Product();
        product2.setProductId("8a76b99c-a0b3-46d2-a688-4c1831b21119");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product2);

        order= new Order(
            "dbd4aff4-9a7f-4603-92c2-eaf529271cc9", 
            products, 
            1708560000L, 
            "Safira Sudrajat"
        );
    }

    void loadBankTransferPaymentData() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "1234567890");
    }

    void loadVoucherCodePaymentData() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    
    @Test
    void testCreatePaymentWithNoOrder() {
        loadBankTransferPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), null, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithNoPaymentMethod() {
        loadBankTransferPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", null, order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithEmptyPaymentData() {
        paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithInvalidPaymentMethod() {
        loadBankTransferPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", "MEOW", order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithDefaultStatus() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_CONFIRMATION.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithSuccessStatus() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData, PaymentStatus.SUCCESS.getValue());
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        loadBankTransferPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData, "MEOW");
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData, PaymentStatus.REJECTED.getValue());
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToRejected() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToInvalidStatus() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToWaitingConfirmation() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.WAITING_CONFIRMATION.getValue());
        assertEquals(PaymentStatus.WAITING_CONFIRMATION.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherButPaymentDataIncorrect() {
        loadBankTransferPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithBankTransferButPaymentDataIncorrect() {
        loadVoucherCodePaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithBankTransferButPaymentDataHasNoBankName() {
        loadBankTransferPaymentData();
        paymentData.remove("bankName");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithBankTransferButPaymentDataHasNoRefferenceCode() {
        loadBankTransferPaymentData();
        paymentData.remove("referenceCode");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherButPaymentDataHasNoVoucherCode() {
        loadVoucherCodePaymentData();
        paymentData.remove("voucherCode");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherSuccess() {
        loadVoucherCodePaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithBankTransferSuccess() {
        loadBankTransferPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.BANK.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_CONFIRMATION.getValue(), payment.getStatus());
        paymentData.clear();
    }
}
