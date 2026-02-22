package com.example.practicelangsir.repository;

import java.util.*;

import com.example.practicelangsir.model.Order;
import com.example.practicelangsir.model.OrderItem;

public interface OrderRepository {

    Optional<Order> findById(int id);

    int createOrder(Order order);

    void saveOrderItem(OrderItem item);

    List<Order> findByCustomerEmail(String email);

    List<OrderItem> findItemsByOrderId(int orderId);

    boolean deleteById(int id);

}
