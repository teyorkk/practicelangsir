package com.example.practicelangsir.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private final int id;
    private final String customerEmail, orderDate;
    private final double totalAmount;
    private final List<OrderItemDetailsDTO> items;
}
