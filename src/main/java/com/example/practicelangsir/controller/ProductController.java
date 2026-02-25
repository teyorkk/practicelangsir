package com.example.practicelangsir.controller;

import java.util.List;
import java.util.Scanner;

import com.example.practicelangsir.dto.ProductDTO;
import com.example.practicelangsir.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductController {
    private final ProductService serve;

    public void handleAddProduct(Scanner inp) {
        System.out.println("Product name: ");
        String name = inp.nextLine();
        System.out.println("Price : ");
        double price = inp.nextDouble();

        inp.nextLine();
        System.out.println("Quantity : ");
        int quantity = inp.nextInt();
        inp.nextLine();
        try {
            serve.addProduct(ProductDTO.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(quantity)
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void handleViewAll() {
        List<ProductDTO> products = serve.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        System.out.printf("%-5s | %-20s | %-10s | %-8s%n", "ID", "Name", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");
        for (ProductDTO p : products) {
            System.out.printf("%-5d | %-20s | ₱%-9.2f | %-8d%n",
                    p.getId(), p.getName(), p.getPrice(), p.getStockQuantity());
        }
    }

    public void handleViewProductById(Scanner inp) {
        System.out.println("Enter id:");
        int id = inp.nextInt();
        inp.nextLine();
        try {
            ProductDTO product = serve.getProductById(id);
            System.out.println("id" + product.getId());

            System.out.println("name" + product.getName());
            System.out.println("price" + product.getPrice());

            System.out.println("quantity" + product.getStockQuantity());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleUpdateProduct(Scanner inp) {
        handleViewAll();
        System.out.println("Enter id:");
        int id = inp.nextInt();
        inp.nextLine();
        try {
            ProductDTO product = serve.getProductById(id);
            System.out.println("Current state of the product");
            System.out.println("id" + product.getId());
            System.out.println("name" + product.getName());
            System.out.println("price" + product.getPrice());
            System.out.println("quantity" + product.getStockQuantity());
            System.out.println("Want to change price (yes/no): ");
            String desc = inp.nextLine();
            double price = product.getPrice();
            int quantity = product.getStockQuantity();
            if (desc.equalsIgnoreCase("Yes")) {
                System.out.println("Enter price: ");
                price = inp.nextDouble();
                inp.nextLine();
            }
            System.out.println("Want to change quantity (yes/no): ");
            desc = inp.nextLine();
            if (desc.equalsIgnoreCase("Yes")) {
                System.out.println("Enter quantity: ");
                quantity = inp.nextInt();
                inp.nextLine();
            }
            serve.updateProduct(id, ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(price)
                    .stockQuantity(quantity)
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleDeleteProduct(Scanner inp) {
        handleViewAll();
        System.out.print("Enter Product ID to delete: ");
        int id = inp.nextInt();
        inp.nextLine(); // Stay clean!

        try {
            serve.deleteProduct(id);
            System.out.println("Product deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
