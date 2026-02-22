package com.example.practicelangsir.repository.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.practicelangsir.config.DatabaseConfig;
import com.example.practicelangsir.model.Order;
import com.example.practicelangsir.model.OrderItem;
import com.example.practicelangsir.repository.OrderRepository;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Optional<Order> findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ? ";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Order.builder()
                            .id(rs.getInt("id"))
                            .customerEmail(rs.getString("customer_email"))
                            .orderDate(rs.getObject("order_date", java.time.LocalDateTime.class))
                            .totalAmount(rs.getDouble("total_amount"))
                            .build());
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders(customer_email, total_amount) VALUES (?, ?)";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getCustomerEmail());
            pstmt.setDouble(2, order.getTotalAmount());
            int holder = pstmt.executeUpdate();
            try (var rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    order.setId(generatedId);
                }
            }
            return holder > 0;
        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }
    }

    @Override
    public boolean saveOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, item.getOrderId());
            pstmt.setInt(2, item.getProductId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getSubtotal());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }
    }

    @Override
    public List<Order> findByCustomerEmail(String email) {
        String sql = "SELECT * FROM orders WHERE customer_email = ?";
        List<Order> orders = new ArrayList<>();
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(Order.builder()
                            .id(rs.getInt("id"))
                            .customerEmail(rs.getString("customer_email"))
                            .orderDate(rs.getObject("order_date", java.time.LocalDateTime.class))
                            .totalAmount(rs.getDouble("total_amount"))
                            .build());

                }

            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }
    }

    @Override
    public List<OrderItem> findItemsByOrderId(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findItemsByOrderId'");
    }

    @Override
    public boolean deleteById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
