package com.example.demo.TheGioiDen.controller;

import com.example.demo.TheGioiDen.Service.OrderService;
import com.example.demo.TheGioiDen.Service.UserService;
import com.example.demo.TheGioiDen.model.Order;
import com.example.demo.TheGioiDen.model.User;
import com.example.demo.TheGioiDen.payload.request.OrderRequest;
import com.example.demo.TheGioiDen.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Order Management", description = "API để quản lý đơn hàng")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Tạo đơn hàng mới",
            description = "Tạo một đơn hàng mới với thông tin địa chỉ, số điện thoại và danh sách sản phẩm")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tạo đơn hàng thành công",
                content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ"),
        @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createOrder(
            @Parameter(description = "Thông tin đơn hàng", required = true)
            @Valid @RequestBody OrderRequest orderRequest) {
        try {
            User user = userService.getCurrentUser();
            Order order = orderService.createOrder(user, orderRequest);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error creating order: " + e.getMessage()));
        }
    }

    @Operation(summary = "Lấy danh sách đơn hàng",
            description = "Lấy danh sách đơn hàng. Admin sẽ thấy tất cả đơn hàng, User chỉ thấy đơn hàng của mình")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
        @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Order>> getUserOrders() {
        try {
            User user = userService.getCurrentUser();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            // Nếu là ADMIN, có thể xem tất cả đơn hàng
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                List<Order> allOrders = orderService.getAllOrders();
                return ResponseEntity.ok(allOrders);
            }
            
            // Nếu là USER, chỉ xem đơn hàng của mình
            List<Order> orders = orderService.getOrdersByUser(user);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Xem chi tiết đơn hàng",
            description = "Xem thông tin chi tiết của một đơn hàng cụ thể")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
        @ApiResponse(responseCode = "400", description = "Không có quyền xem đơn hàng này"),
        @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOrder(
            @Parameter(description = "ID của đơn hàng", required = true)
            @PathVariable Long id) {
        try {
            User currentUser = userService.getCurrentUser();
            Order order = orderService.getOrderById(id);
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            // Kiểm tra quyền truy cập
            if (!isAdmin && !order.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You don't have permission to view this order"));
            }
            
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @Operation(summary = "Cập nhật trạng thái đơn hàng",
            description = "Cập nhật trạng thái của đơn hàng (PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
        @ApiResponse(responseCode = "400", description = "Trạng thái không hợp lệ"),
        @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateOrderStatus(
            @Parameter(description = "ID của đơn hàng", required = true)
            @PathVariable Long id,
            @Parameter(description = "Trạng thái mới (PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED)", required = true)
            @RequestParam String status) {
        try {
            Order order = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Invalid status value"));
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Xóa đơn hàng",
            description = "Xóa một đơn hàng khỏi hệ thống (chỉ Admin)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Xóa thành công"),
        @ApiResponse(responseCode = "401", description = "Chưa đăng nhập"),
        @ApiResponse(responseCode = "403", description = "Không có quyền truy cập"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(
            @Parameter(description = "ID của đơn hàng cần xóa", required = true)
            @PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(new MessageResponse("Order deleted successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
} 