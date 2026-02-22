package com.example.practicelangsir.exception;

public class InsufficientStockException extends OrderException {
    public InsufficientStockException(String productName, int reqQuantity, int avaiable) {
        super("You ordered " + reqQuantity + " " + productName + " ,but it only have " + avaiable);
    }
}
