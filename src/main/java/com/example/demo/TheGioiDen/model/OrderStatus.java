package com.example.demo.TheGioiDen.model;

public enum OrderStatus {
    PENDING,        // Đơn hàng mới tạo
    CONFIRMED,      // Đã xác nhận đơn hàng
    SHIPPING,       // Đang giao hàng
    DELIVERED,      // Đã giao hàng thành công
    CANCELLED       // Đã hủy
} 