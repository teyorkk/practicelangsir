package com.example.practicelangsir.repository;

import java.util.*;

import com.example.practicelangsir.model.Product;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(int id);

    List<Product> findAllProducts();

    void update(Product product);

    boolean updateStock(int productId, int quantityChange);

    boolean deleteById(int id);
}
