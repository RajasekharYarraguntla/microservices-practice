package com.raja.controller;

import com.raja.entity.Payment;
import com.raja.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService service) {
        this.paymentService = service;
    }

    @PostMapping("/pay/{orderId}")
    public Payment makePayment(@PathVariable Long orderId, @RequestParam Double amount) {
        return paymentService.makePayment(orderId, amount);
    }

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrder(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }
}
