package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllUsers();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void deleteUser(Long userId);
    User updateUser(User user);
    User getCurrentUser();
} 