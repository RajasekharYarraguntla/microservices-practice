package com.raja.service;

import com.raja.dto.Order;
import com.raja.entity.Payment;
import com.raja.feign.OrderClient;
import com.raja.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentServiceImpl(PaymentRepository repo, OrderClient orderClient) {
        this.paymentRepository = repo;
        this.orderClient = orderClient;
    }

    @Override
    public Payment makePayment(Long orderId, Double amount) {
        // Validate Order using Feign
        Order order = orderClient.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found. Cannot process payment.");
        }

        String status = order.getPrice().equals(amount) ? "SUCCESS" : "FAILED";

        Payment payment = new Payment(orderId, amount, status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        Order order = orderClient.getOrderById(orderId);// Validate Order existence
        payment.setOrder(order);
        return payment;

    }
}
