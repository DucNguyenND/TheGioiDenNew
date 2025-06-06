package com.example.demo.TheGioiDen.repository;

import com.example.demo.TheGioiDen.model.Cart;
import com.example.demo.TheGioiDen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
} 