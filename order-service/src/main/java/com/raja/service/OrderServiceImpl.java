package com.raja.service;


import com.raja.dto.User;
import com.raja.entity.Order;
import com.raja.feign.UserService;
import com.raja.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, OrderRepository repo) {
        this.userService = userService;
        this.orderRepository = repo;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> all = orderRepository.findAll();
        all.forEach(order -> {
            User user = userService.getUserById(order.getUserId());
            order.setUser(user);
        });
        return all;
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        User user = userService.getUserById(order.getUserId());
        order.setUser(user);
        return order;
    }

    @Override
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackCreateOrder")
    public Order createOrder(Order order) {
        User user = userService.getUserById(order.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found. Cannot create order.");
        }
        return orderRepository.save(order);
    }

    public Order fallbackCreateOrder(Order order, Throwable throwable) {
        System.out.println("⚠️ Circuit Breaker triggered. Reason: " + throwable.getMessage());
        order.setProductName(order.getProductName() + " (Pending User Verification)");
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order order = getOrderById(id);
        order.setProductName(updatedOrder.getProductName());
        order.setQuantity(updatedOrder.getQuantity());
        order.setPrice(updatedOrder.getPrice());
        order.setUserId(updatedOrder.getUserId());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.forEach(order -> {
            User user = userService.getUserById(order.getUserId());
            order.setUser(user);
        });
        return orders;
    }
}
