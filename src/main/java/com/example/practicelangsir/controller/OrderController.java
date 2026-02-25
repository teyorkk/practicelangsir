package com.example.practicelangsir.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.practicelangsir.dto.OrderItemDetailsDTO;
import com.example.practicelangsir.dto.OrderItemRequest;
import com.example.practicelangsir.dto.OrderRequest;
import com.example.practicelangsir.dto.OrderResponse;
import com.example.practicelangsir.exception.ResourceNotFoundException;
import com.example.practicelangsir.service.OrderService;
import com.example.practicelangsir.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;

    public void handlePlaceOrder(Scanner inp) {
        List<OrderItemRequest> items = new ArrayList<>();

        System.out.print("Enter Customer Email: ");
        String email = inp.nextLine();

        boolean adding = true;
        while (adding) {
            System.out.println("\n--- Available Products ---");
            productService.getAllProducts().forEach(p -> System.out.printf("[%d] %s - ₱%.2f (Stock: %d)%n", p.getId(),
                    p.getName(), p.getPrice(), p.getStockQuantity()));

            System.out.print("Enter Product ID (or 0 to checkout): ");
            int id = inp.nextInt();

            if (id == 0)
                break;

            System.out.print("Enter Quantity: ");
            int quantity = inp.nextInt();
            inp.nextLine();

            items.add(OrderItemRequest.builder()
                    .productId(id)
                    .quantity(quantity)
                    .build());

            System.out.print("Add another item? (y/n): ");
            if (inp.nextLine().equalsIgnoreCase("n")) {
                adding = false;
            }
        }

        try {
            orderService.placeOrder(OrderRequest.builder()
                    .customerEmail(email)
                    .items(items)
                    .build());

            System.out.println("Order Placed Successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleViewHistory(Scanner inp) {
        System.out.print("Enter Customer Email to view history: ");
        String email = inp.nextLine();

        try {
            List<OrderResponse> history = orderService.getOrdersByCustomer(email);

            System.out.println("\n======= ORDER HISTORY FOR: " + email + " =======");

            for (OrderResponse response : history) {
                System.out.println("-------------------------------------------");
                System.out.println("ORDER ID: " + response.getId());
                System.out.println("DATE:     " + response.getOrderDate());
                System.out.println("ITEMS:");

                for (OrderItemDetailsDTO item : response.getItems()) {
                    System.out.printf("  - %-15s | Qty: %-3d | Sub: %.2f%n",
                            item.getProductName(), item.getQuantity(), item.getSubtotal());
                }

                System.out.println("TOTAL AMOUNT: " + String.format("%.2f", response.getTotalAmount()));
            }
            System.out.println("===========================================\n");

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error retrieving history: " + e.getMessage());
        }
    }

    public void handleCancelOrder(Scanner inp) {
        System.out.print("Enter Customer Email to see your orders: ");
        String email = inp.nextLine();

        try {
            List<OrderResponse> orders = orderService.getOrdersByCustomer(email);

            System.out.println("\n--- Your Active Orders ---");
            for (OrderResponse response : orders) {
                System.out.printf("ID: %d | Date: %s | Total: ₱%.2f%n",
                        response.getId(), response.getOrderDate(), response.getTotalAmount());
            }

            System.out.print("\nEnter Order ID to cancel (or 0 to go back): ");
            int orderId = inp.nextInt();
            inp.nextLine();

            if (orderId == 0)
                return;

            boolean ownsOrder = orders.stream().anyMatch(o -> o.getId() == orderId);

            if (ownsOrder) {
                orderService.cancelOrder(orderId);
                System.out.println(" Order has been canceled and stock restored.");
            } else {
                System.out.println("That Order ID does not belong to your account.");
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
