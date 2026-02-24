package com.example.practicelangsir.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.practicelangsir.dto.ProductDTO;
import com.example.practicelangsir.model.Product;
import com.example.practicelangsir.repository.ProductRepository;
import com.example.practicelangsir.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    @Override
    public void addProduct(ProductDTO dto) throws IllegalArgumentException, RuntimeException {
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (dto.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("Stock should not be 0 or negative");
        }
        if (repo.existByName(dto.getName()).isEmpty()) {
            throw new RuntimeException("This product is already in our system");
        }

        Product product = Product.builder()
                .name(dto.getName())
                .stockquantity(dto.getStockQuantity())
                .price(dto.getPrice())
                .build();

        repo.save(product);
    }

    @Override
    public ProductDTO getProductById(int id) throws RuntimeException {
        return repo.findById(id).map(p -> ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stockQuantity(p.getStockquantity())
                .build()).orElseThrow(() -> new RuntimeException("Product seem not to exist"));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repo.findAllProducts().stream().map(p -> ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stockQuantity(p.getStockquantity())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void updateProduct(int id, ProductDTO dto) {
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (dto.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("Stock should not be 0 or negative");
        }
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setStockquantity(dto.getStockQuantity());

        repo.update(existing);
    }

    @Override
    public void deleteProduct(int id) throws RuntimeException {
        if (repo.findById(id).isEmpty()) {
            throw new RuntimeException("Product seem not to exist");
        }
        repo.deleteById(id);
    }

}
