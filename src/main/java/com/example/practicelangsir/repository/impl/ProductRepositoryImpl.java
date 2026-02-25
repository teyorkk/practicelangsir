package com.example.practicelangsir.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Statement;

import com.example.practicelangsir.config.DatabaseConfig;
import com.example.practicelangsir.model.Product;
import com.example.practicelangsir.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public boolean save(Product product) {
        String sql = "INSERT INTO products (name,price,stock_quantity) VALUES (?,?,?)";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getStockquantity());

            int holder = pstmt.executeUpdate();

            try (var rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    product.setId(generatedId);
                }
            }
            return holder > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Product.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .price(rs.getDouble("price"))
                            .stockquantity(rs.getInt("stock_quantity"))
                            .build());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());

        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.createStatement();
                var rs = pstmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .price(rs.getDouble("price"))
                        .stockquantity(rs.getInt("stock_quantity"))
                        .build();
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());
        }

        return products;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ? , price = ?, stock_quantity = ? WHERE id = ?";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getStockquantity());
            pstmt.setInt(4, product.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());

        }
    }

    @Override
    public boolean updateStock(int productId, int quantityChange) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity + ? WHERE id = ?";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantityChange);
            pstmt.setInt(2, productId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());

        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM products WHERE id= ?";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage());

        }
    }

    public String existByName(String name) {
        String sql = "SELECT name FROM products WHERE name = ? LIMIT 1";

        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB ERROR: " + e.getMessage(), e);
        }
    }

}
