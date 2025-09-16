package com.raja.service;

import com.raja.entity.Payment;

public interface PaymentService {
    Payment makePayment(Long orderId, Double amount);
    Payment getPaymentByOrderId(Long orderId);
}
