package com.example.practicelangsir;

import com.example.practicelangsir.controller.OrderController;
import com.example.practicelangsir.controller.ProductController;
import com.example.practicelangsir.repository.impl.OrderRepositoryImpl;
import com.example.practicelangsir.repository.impl.ProductRepositoryImpl;
import com.example.practicelangsir.service.impl.OrderServiceImpl;
import com.example.practicelangsir.service.impl.ProductServiceImpl;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        var productRepo = new ProductRepositoryImpl();
        var orderRepo = new OrderRepositoryImpl();

        var productServ = new ProductServiceImpl(productRepo);
        var orderServ = new OrderServiceImpl(productRepo, orderRepo);

        var productCtrl = new ProductController(productServ);
        var orderCtrl = new OrderController(orderServ, productServ);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                runApp(productCtrl, orderCtrl);
            });
        }
    }

    private static void runApp(ProductController productCtrl, OrderController orderCtrl) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> productCtrl.handleViewAll();
                case 2 -> productCtrl.handleAddProduct(sc);
                case 3 -> orderCtrl.handlePlaceOrder(sc);
                case 4 -> orderCtrl.handleViewHistory(sc);
                case 5 -> orderCtrl.handleCancelOrder(sc);
                case 6 -> productCtrl.handleUpdateProduct(sc);
                case 0 -> running = false;
                default -> System.out.println("Unknown option.");
            }
        }
        System.out.println("Exiting System... Goodbye, Moises!");
    }

    private static void displayMenu() {
        System.out.println("\n==============================");
        System.out.println("    E-COMMERCE SYSTEM (V8)    ");
        System.out.println("==============================");
        System.out.println("1. View Catalog");
        System.out.println("2. Add New Product");
        System.out.println("3. Place New Order");
        System.out.println("4. View My Order History");
        System.out.println("5. Cancel an Order");
        System.out.println("6. Update Product Details");
        System.out.println("0. Exit");
        System.out.print("Selection > ");
    }
}