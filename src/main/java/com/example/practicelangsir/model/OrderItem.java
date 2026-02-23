package com.example.practicelangsir.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    private int orderId, productId, quantity;
    private double subtotal;
    private String productName;
}
