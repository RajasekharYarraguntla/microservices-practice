package com.raja.entity;

import com.raja.dto.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Integer quantity;
    private Double price;
    private Long userId; // foreign key reference to User Service

    @Transient
    private User user;

    public Order() {}

    public Order(String productName, Integer quantity, Double price, Long userId) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

}
