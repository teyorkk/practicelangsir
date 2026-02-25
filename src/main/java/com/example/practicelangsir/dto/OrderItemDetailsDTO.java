package com.example.practicelangsir.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDetailsDTO {
    private final String productName;
    private final int quantity;
    private final double subtotal;
}
