package com.example.demo.TheGioiDen.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private double totalPrice;

    public void updateTotalPrice() {
        this.totalPrice = items.stream()
                .mapToDouble(item ->Integer.parseInt( item.getProduct().getGiaSp()) * item.getQuantity())
                .sum();
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
        updateTotalPrice();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
        updateTotalPrice();
    }
} 