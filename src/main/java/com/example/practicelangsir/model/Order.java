package com.example.practicelangsir.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private int id;
    private String customerEmail;
    private LocalDate orderDate;
    private double totalAmount;
}
