package com.example.practicelangsir.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private int stockquantity;
}
