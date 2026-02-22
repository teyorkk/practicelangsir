package com.example.practicelangsir.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemRequest {
    private final int productId, quantity;
}
