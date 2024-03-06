package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.PaymentDTO;
import id.ac.ui.cs.advprog.eshop.service.PaymentServiceImpl;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentService;

    @GetMapping("/detail")
    public String getPaymentDetail(Model model){
        return "paymentdetail";
    }
    
    @GetMapping("/detail/{id}")
    public String getPaymentDetailById(Model model, @PathVariable("id") String id){
        Payment payment = paymentService.getPayment(id);
        model.addAttribute("payment", payment);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReferenceCode(payment.getPaymentData().get("referenceCode"));
        paymentDTO.setReferenceCode(payment.getPaymentData().get("bankName"));
        paymentDTO.setVoucherCode(payment.getPaymentData().get("voucherCode"));
        model.addAttribute("paymentInfo", paymentDTO);
        return "paymentDetail";
    }

    @GetMapping("/admin/list")
    public String getPaymentList(Model model){
        model.addAttribute("payments", paymentService.getAllPayments());
        return "paymentlist";
    }

    @GetMapping("/admin/detail/{id}")
    public String getPaymentIdAdmin(@PathVariable("id")String paymentId, Model model){
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReferenceCode(payment.getPaymentData().get("referenceCode"));
        paymentDTO.setReferenceCode(payment.getPaymentData().get("bankName"));
        paymentDTO.setVoucherCode(payment.getPaymentData().get("voucherCode"));
        model.addAttribute("paymentInfo", paymentDTO);

        model.addAttribute("admin", true);
        return "paymentDetail";
    }

    @PostMapping("/admin/set-status/{id}")
    public String postPaymentAdminStatus(@PathVariable("id")String paymentId, @ModelAttribute("status") String status, Model model){
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:../list";
    }
}
