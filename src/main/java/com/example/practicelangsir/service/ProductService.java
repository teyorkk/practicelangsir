package com.example.practicelangsir.service;

import com.example.practicelangsir.dto.ProductDTO;
import java.util.*;

public interface ProductService {
    void addProduct(ProductDTO dto);

    ProductDTO getProductById(int id) throws RuntimeException;

    List<ProductDTO> getAllProducts();

    void updateProduct(int id, ProductDTO dto);

    void deleteProduct(int id) throws RuntimeException;
}
