package com.example.demo.TheGioiDen.model;

import com.example.demo.TheGioiDen.entity.SanPham;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private SanPham product;

    private int quantity;

    public double getSubtotal() {
        return Integer.parseInt( product.getGiaSp()) * quantity;
    }
} 