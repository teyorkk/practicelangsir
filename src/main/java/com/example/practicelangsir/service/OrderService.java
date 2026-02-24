package com.example.practicelangsir.service;

import com.example.practicelangsir.dto.OrderRequest;
import com.example.practicelangsir.dto.OrderResponse;

import java.util.*;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);

    Optional<OrderResponse> getOrderDetails(int orderId);

    List<OrderResponse> getOrdersByCustomer(String email);

    void cancelOrder(int orderId);
}
