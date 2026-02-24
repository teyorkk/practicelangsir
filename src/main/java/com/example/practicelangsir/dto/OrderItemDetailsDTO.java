package com.example.practicelangsir.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDetailsDTO {
    private final String productName;
    private final int id, quantity;
    private final double subtotal;
}
