package com.raja.service;

import com.raja.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
    List<Order> getOrdersByUserId(Long userId);
}
