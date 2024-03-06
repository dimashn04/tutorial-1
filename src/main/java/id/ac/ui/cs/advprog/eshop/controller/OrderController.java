package id.ac.ui.cs.advprog.eshop.controller;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.OrderDTO;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentDTO;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        List<Product> products = productService.findAll();
        OrderDTO orderDto = new OrderDTO();

        for (Product p : products) {
            orderDto.addProduct(p);
        }

        model.addAttribute("form", orderDto);
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("form") OrderDTO orders, Model model) {
        List<Product> products = new ArrayList<>();
        List<Product> orderedProducts = orders.getProducts();

        for (Product p : orderedProducts) {
            Product product = productService.findById(p.getProductId());
            p.setProductName(product.getProductName());
            products.add(p);
        }

        LocalTime currentTime = LocalTime.now();
        Time orderTime = Time.valueOf(currentTime);

        Order newOrder = new Order(
            UUID.randomUUID().toString(),
            products,
            orderTime.getTime(),
            orders.getAuthor()
        ); 
        orderService.createOrder(newOrder);

        return "redirect:history";
    }

    @GetMapping("/history")
    public String getOrderHistory(Model model) {
        model.addAttribute("author", new OrderDTO());
        return "getOrderHistory";
    }

    @PostMapping("/history")
    public String postOrderHistory(@ModelAttribute("author") OrderDTO order, Model model) {
        List<Order> orders = orderService.findAllByAuthor(order.getAuthor());
        model.addAttribute("orders", orders);
        return "orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String getPayOrder(Model model, @PathVariable("orderId") String orderId) {
        PaymentDTO payment = new PaymentDTO();
        payment.setOrderId(orderId);

        model.addAttribute("paymentInfo", payment);
        model.addAttribute("order", orderId);

        return "payOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String postPayOrder(@ModelAttribute("paymentInfo") PaymentDTO payment, @PathVariable("orderId") String orderId, Model model) {
        Map<String, String> paymentData = new HashMap<>();
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(orderId);
        System.out.println(payment.getMethod());

        if (payment.getMethod().equals(PaymentMethod.VOUCHER.getValue())) {
            if (payment.getVoucherCode() == null) {
                payment.setVoucherCode("");
            }

            paymentData.put("voucherCode", payment.getVoucherCode());
            paymentDTO.setVoucherCode(payment.getVoucherCode());
        } else if (payment.getMethod().equals(PaymentMethod.BANK.getValue())) {
            if (payment.getBankName()==null){
                payment.setBankName("");
            }

            if (payment.getReferenceCode()==null){
                payment.setReferenceCode("");
            }

            paymentData.put("bankName", payment.getBankName());
            paymentData.put("referenceCode", payment.getReferenceCode());
            paymentDTO.setBankName(payment.getBankName());
            paymentDTO.setReferenceCode(payment.getReferenceCode());
        }

        Payment newPayment = paymentService.addPayment(
            orderService.findById(orderId), 
            paymentDTO.getMethod(), 
            paymentData
        );

        model.addAttribute("payment", newPayment);
        model.addAttribute("paymentInfo", paymentDTO);
        return "payOrderAfter";
    }
}
