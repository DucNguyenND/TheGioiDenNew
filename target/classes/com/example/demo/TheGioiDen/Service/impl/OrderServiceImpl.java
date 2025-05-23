package com.example.demo.TheGioiDen.Service.impl;

import com.example.demo.TheGioiDen.Repository.OrderRepository;
import com.example.demo.TheGioiDen.Service.OrderService;
import com.example.demo.TheGioiDen.Service.TheGioiDenService;
import com.example.demo.TheGioiDen.entity.Product;
import com.example.demo.TheGioiDen.model.Order;
import com.example.demo.TheGioiDen.model.OrderItem;
import com.example.demo.TheGioiDen.model.OrderStatus;
import com.example.demo.TheGioiDen.model.User;
import com.example.demo.TheGioiDen.payload.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TheGioiDenService productService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOrder(User user, OrderRequest orderRequest) {
        Order order = new Order(user, orderRequest.getShippingAddress(), orderRequest.getPhoneNumber());

        for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productService.getSanPhamByIdReturnProduct(itemRequest.getProductId().intValue());
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + itemRequest.getProductId());
            }

            OrderItem orderItem = new OrderItem(
                order,
                product,
                itemRequest.getQuantity(),
                BigDecimal.valueOf(Long.parseLong(product.getGiaSp()))
            );
            order.addOrderItem(orderItem);
        }

        // Calculate total amount
        BigDecimal total = order.getOrderItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order updateOrderStatus(Long orderId, String status) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            
            OrderStatus newStatus;
            try {
                newStatus = OrderStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid order status: " + status);
            }
            
            // Kiểm tra logic chuyển trạng thái
            if (newStatus == OrderStatus.CANCELLED) {
                if (order.getStatus() == OrderStatus.SHIPPING) {
                    throw new IllegalStateException("Cannot cancel order that is being shipped");
                }
                if (order.getStatus() == OrderStatus.DELIVERED) {
                    throw new IllegalStateException("Cannot cancel order that has been delivered");
                }
            }

            // Kiểm tra logic chuyển trạng thái khác
            switch (order.getStatus()) {
                case CANCELLED:
                    throw new IllegalStateException("Cannot change status of cancelled order");
                case DELIVERED:
                    throw new IllegalStateException("Cannot change status of delivered order");
                default:
                    break;
            }
            
            order.setStatus(newStatus);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error updating order status: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
} 