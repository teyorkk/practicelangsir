package com.example.practicelangsir.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.practicelangsir.config.DatabaseConfig;
import com.example.practicelangsir.model.Product;
import com.example.practicelangsir.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (name,price,stock_quantity) VALUES (?,?,?)";
        try (var conn = DatabaseConfig.getConnection();
                var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getStockquantity());

            pstmt.executeUpdate();

            try (var rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    product.setId(generatedId);
                }
            }

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
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql)) {

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
    public void update(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean updateStock(int productId, int quantityChange) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStock'");
    }

    @Override
    public boolean deleteById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
