package com.example.practicelangsir.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {
    private final String customerEmail;
    private final List<OrderItemRequest> items;
}
