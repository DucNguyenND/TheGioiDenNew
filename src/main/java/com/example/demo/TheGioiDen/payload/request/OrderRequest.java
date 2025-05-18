package com.example.demo.TheGioiDen.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderRequest {
    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String phoneNumber;

    @NotEmpty
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
} 