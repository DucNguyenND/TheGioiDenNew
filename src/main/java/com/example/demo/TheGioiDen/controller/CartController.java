package com.example.demo.TheGioiDen.controller;

import com.example.demo.TheGioiDen.Service.CartService;
import com.example.demo.TheGioiDen.Service.TheGioiDenService;
import com.example.demo.TheGioiDen.Service.UserService;
import com.example.demo.TheGioiDen.entity.Product;
import com.example.demo.TheGioiDen.model.Cart;
import com.example.demo.TheGioiDen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private TheGioiDenService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        User user = userService.getCurrentUser();
        Cart cart = cartService.getCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable int productId,
            @RequestParam(defaultValue = "1") int quantity) {
        User user = userService.getCurrentUser();
        Product product = productService.getSanPhamByIdReturnProduct(productId);
        
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.addToCart(user, product, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        User user = userService.getCurrentUser();
        cartService.removeFromCart(user, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        if (quantity < 1) {
            return ResponseEntity.badRequest().body("Quantity must be greater than 0");
        }

        User user = userService.getCurrentUser();
        cartService.updateQuantity(user, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        User user = userService.getCurrentUser();
        cartService.clearCart(user);
        return ResponseEntity.ok().build();
    }
} 