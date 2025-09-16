package com.raja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private String productName;
    private Integer quantity;
    private Double price;
    private User user;
}