package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.entity.SanPham;
import com.example.demo.TheGioiDen.model.Cart;
import com.example.demo.TheGioiDen.model.CartItem;
import com.example.demo.TheGioiDen.model.User;
import com.example.demo.TheGioiDen.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart getOrCreateCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    @Transactional
    public void addToCart(User user, SanPham product, int quantity) {
        Cart cart = getOrCreateCart(user);
        
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Nếu sản phẩm chưa có, tạo mới CartItem
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.addItem(newItem);
        }

        cart.updateTotalPrice();
        cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(User user, Long productId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }

    @Transactional
    public void updateQuantity(User user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    cart.updateTotalPrice();
                    cartRepository.save(cart);
                });
    }

    public Cart getCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }
} 