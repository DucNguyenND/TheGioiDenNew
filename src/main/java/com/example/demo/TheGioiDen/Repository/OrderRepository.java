package com.example.demo.TheGioiDen.repository;

import com.example.demo.TheGioiDen.model.Order;
import com.example.demo.TheGioiDen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByOrderDateDesc(User user);
} 