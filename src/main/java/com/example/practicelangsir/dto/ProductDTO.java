package com.example.practicelangsir.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDTO {
    private final int id, stockQuantity;
    private final String name;
    private final double price;
}
