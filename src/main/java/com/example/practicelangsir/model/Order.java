package com.example.practicelangsir.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private int id;
    private String customerEmail;
    private LocalDateTime orderDate;
    private double totalAmount;
}
