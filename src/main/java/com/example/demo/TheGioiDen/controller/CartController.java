package com.example.demo.TheGioiDen.controller;

import com.example.demo.TheGioiDen.model.Cart;
import com.example.demo.TheGioiDen.model.Product;
import com.example.demo.TheGioiDen.model.User;
import com.example.demo.TheGioiDen.service.CartService;
import com.example.demo.TheGioiDen.service.ProductService;
import com.example.demo.TheGioiDen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Cart cart = cartService.getCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.addToCart(user, product, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long productId,
            Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        cartService.removeFromCart(user, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity,
            Authentication authentication) {
        if (quantity < 1) {
            return ResponseEntity.badRequest().body("Quantity must be greater than 0");
        }

        User user = userService.getCurrentUser(authentication);
        cartService.updateQuantity(user, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        cartService.clearCart(user);
        return ResponseEntity.ok().build();
    }
} 