package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.model.Order;
import com.example.demo.TheGioiDen.model.User;
import com.example.demo.TheGioiDen.payload.request.OrderRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderRequest orderRequest);
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUser(User user);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId, String status);
    void deleteOrder(Long orderId);
} 