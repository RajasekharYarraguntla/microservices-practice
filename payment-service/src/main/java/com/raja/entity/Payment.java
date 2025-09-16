package com.raja.entity;

import com.raja.dto.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Double amount;
    private String status;

    @Transient
    private Order order;// SUCCESS / FAILED

    public Payment() {}

    public Payment(Long orderId, Double amount, String status) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }
}
