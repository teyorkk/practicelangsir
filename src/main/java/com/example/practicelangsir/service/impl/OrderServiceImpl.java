package com.example.practicelangsir.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.practicelangsir.dto.OrderItemDetailsDTO;
import com.example.practicelangsir.dto.OrderItemRequest;
import com.example.practicelangsir.dto.OrderRequest;
import com.example.practicelangsir.dto.OrderResponse;
import com.example.practicelangsir.exception.InsufficientStockException;
import com.example.practicelangsir.exception.ResourceNotFoundException;
import com.example.practicelangsir.model.Order;
import com.example.practicelangsir.model.OrderItem;
import com.example.practicelangsir.model.Product;
import com.example.practicelangsir.repository.OrderRepository;
import com.example.practicelangsir.repository.ProductRepository;
import com.example.practicelangsir.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain atleast one item");
        }
        double totalprice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be atleast 1");
            }
            if (item.getQuantity() > product.getStockquantity()) {
                throw new InsufficientStockException(product.getName(),
                        item.getQuantity(), product.getStockquantity());
            }
            totalprice += product.getPrice() * item.getQuantity();
            orderItems.add(OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(item.getQuantity())
                    .subtotal(product.getPrice() * item.getQuantity())
                    .build());
        }

        Order order = Order.builder()
                .customerEmail(request.getCustomerEmail())
                .totalAmount(totalprice)
                .build();

        orderRepo.createOrder(order);
        List<OrderItemDetailsDTO> orderItemDetailsDTO = new ArrayList<>();

        for (OrderItem itemDetails : orderItems) {
            itemDetails.setOrderId(order.getId());

            orderRepo.saveOrderItem(itemDetails);
            productRepo.updateStock(itemDetails.getProductId(), -itemDetails.getQuantity());
            orderItemDetailsDTO.add(OrderItemDetailsDTO.builder()
                    .productName(itemDetails.getProductName())
                    .quantity(itemDetails.getQuantity())
                    .subtotal(itemDetails.getSubtotal())
                    .build());
        }

        return OrderResponse.builder()
                .id(order.getId())
                .customerEmail(request.getCustomerEmail())
                .items(orderItemDetailsDTO)
                .totalAmount(totalprice)
                .build();

    }

    @Override
    public Optional<OrderResponse> getOrderDetails(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderDetails'");
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByCustomer'");
    }

    @Override
    public void cancelOrder(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }
}
