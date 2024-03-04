package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.BankPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Spy
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setup() {
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9", 
            products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP00000000AAA");
        Payment voucherPayment = new VoucherPayment(
            "c0f81308-9911-40c5-8da4-fa3194485aa1",
            PaymentMethod.VOUCHER.getValue(),
            order,
            voucherPaymentData
        );

        Map<String, String> bankPaymentData = new HashMap<>();
        bankPaymentData.put("bankName", "BNI");
        bankPaymentData.put("referenceCode", "1234567890");
        Payment bankPayment = new BankPayment(
            "c0f81308-9911-40c5-8da4-fa3194485aa1",
            PaymentMethod.BANK.getValue(),
            order,
            bankPaymentData
        );

        payments.add(voucherPayment);
        payments.add(bankPayment);
    }

    @Test
    void testAddBankPayment() {
        Payment bankPayment = payments.get(1);
        doReturn(bankPayment).when(paymentRepository).save(any(Payment.class));
        
        Payment result = paymentService.addPayment(
            bankPayment.getMethod(),
            bankPayment.getOrder(),
            bankPayment.getPaymentData()
        );

        verify(paymentRepository, times(1)).save(bankPayment);
        assertEquals(bankPayment.getId(), result.getId());
        assertEquals(bankPayment.getMethod(), result.getMethod());
        assertEquals(bankPayment.getOrder(), result.getOrder());
        assertEquals(bankPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
    }

    @Test
    void testAddVoucherPayment() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));
        
        Payment result = paymentService.addPayment(
            voucherPayment.getMethod(),
            voucherPayment.getOrder(),
            voucherPayment.getPaymentData()
        );

        verify(paymentRepository, times(1)).save(voucherPayment);
        assertEquals(voucherPayment.getId(), result.getId());
        assertEquals(voucherPayment.getMethod(), result.getMethod());
        assertEquals(voucherPayment.getOrder(), result.getOrder());
        assertEquals(voucherPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
    }

    @Test
    void testAddBankPaymentButOrderAlreadyExist() {
        Payment bankPayment = payments.get(1);
        doReturn(bankPayment).when(paymentRepository).findById(bankPayment.getId());

        assertNull(paymentService.addPayment(
            bankPayment.getMethod(),
            bankPayment.getOrder(),
            bankPayment.getPaymentData()
        ));
        verify(paymentRepository, times(0)).save(bankPayment);
    }

    @Test
    void testAddVoucherPaymentButOrderAlreadyExist() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());

        assertNull(paymentService.addPayment(
            voucherPayment.getMethod(),
            voucherPayment.getOrder(),
            voucherPayment.getPaymentData()
        ));
        verify(paymentRepository, times(0)).save(voucherPayment);
    }

    @Test
    void testUpdateStatusBankPayment() {
        Payment bankPayment = payments.get(1);
        Payment newBankPayment = new BankPayment(
            bankPayment.getId(),
            bankPayment.getMethod(),
            bankPayment.getOrder(),
            bankPayment.getPaymentData(),
            PaymentStatus.SUCCESS.getValue()
        );

        doReturn(bankPayment).when(paymentRepository).findById(bankPayment.getId());
        doReturn(newBankPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(
            bankPayment.getId(),
            PaymentStatus.SUCCESS.getValue()
        );

        assertEquals(bankPayment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(bankPayment.getMethod(), result.getMethod());
        assertEquals(bankPayment.getOrder(), result.getOrder());
        assertEquals(bankPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.BANK.getValue(), result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusVoucherPayment() {
        Payment voucherPayment = payments.get(0);
        Payment newVoucherPayment = new VoucherPayment(
            voucherPayment.getId(),
            voucherPayment.getMethod(),
            voucherPayment.getOrder(),
            voucherPayment.getPaymentData(),
            PaymentStatus.SUCCESS.getValue()
        );

        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());
        doReturn(newVoucherPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(
            voucherPayment.getId(),
            PaymentStatus.SUCCESS.getValue()
        );

        assertEquals(voucherPayment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(voucherPayment.getMethod(), result.getMethod());
        assertEquals(voucherPayment.getOrder(), result.getOrder());
        assertEquals(voucherPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusBankPaymentInvalidStatus() {
        Payment bankPayment = payments.get(1);
        doReturn(bankPayment).when(paymentRepository).findById(bankPayment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(bankPayment.getId(), "MEOW");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusVoucherPaymentInvalidStatus() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(voucherPayment.getId(), "MEOW");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusBankPaymentInvalidId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus("zczc", PaymentStatus.SUCCESS.getValue());
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getOrder(), result.getOrder());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertNull(paymentService.getPayment("zczc"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(payments, result);
    }
}
