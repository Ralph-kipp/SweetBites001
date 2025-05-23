package org.example.Service;

import org.example.dto.*;
import org.example.Customer;
import org.example.Order;
import org.example.Product;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(OrderRequest orderRequest) {
        // 1. Extract customer information from the request
        Customer customerDetails = orderRequest.getCustomer();

        // 2. Check if the customer already exists (e.g., by phone number)
        Optional<Customer> existingCustomer = customerRepository.findByPhoneNumber(customerDetails.getPhoneNumber());
        Customer customer = existingCustomer.orElseGet(() -> customerRepository.save(customerDetails));

        // 3. Create a new Order entity
        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliveryDate(LocalDate.parse(orderRequest.getDeliveryDate()));
        order.setDeliveryZone(orderRequest.getDeliveryZone()); // Set delivery zone here
        order.setOrderStatus("PENDING"); // Initial status
        order.setProducts(new ArrayList<>()); // Initialize the list of products

        double totalProductCost = 0.0;
        List<OrderItemRequest> orderItems = orderRequest.getOrderItems();

        // 4. Handle order items (retrieve products, associate, and calculate cost)
        for (OrderItemRequest itemRequest : orderItems) {
            Long productId = itemRequest.getProductId();
            int quantity = itemRequest.getQuantity();
            Optional<Product> productOptional = productRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                order.getProducts().add(product);
                totalProductCost += product.getPrice() * quantity;
                // Note: We are now using the quantity from OrderItemRequest.
            } else {
                throw new RuntimeException("Product with ID " + productId + " not found.");
            }
        }

        // Calculate delivery cost based on deliveryZone
        double deliveryCost = calculateDeliveryCost(orderRequest.getDeliveryZone());

        // Calculate the total cost
        double totalCost = totalProductCost + deliveryCost;
        order.setTotalCost(totalCost);

        // 5. Save the order (with the total cost now set)
        Order savedOrder = orderRepository.save(order);

        // 6. Return the saved order (or at least its ID)
        return savedOrder;
    }

    // Helper method to calculate delivery cost based on zone
    private double calculateDeliveryCost(String deliveryZone) {
        // This is a placeholder for your actual delivery cost logic
        switch (deliveryZone.toLowerCase()) {
            case "near":
                return 50.0; // Example cost
            case "mid":
                return 100.0; // Example cost
            case "far":
                return 150.0; // Example cost
            default:
                return 0.0; // Default or handle invalid zone
        }
    }

    public Order confirmOrder(Long orderId) {
        // 1. Retrieve the Order by its ID
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        // 2. Check if the order exists
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            // 3. Check if the order is already confirmed or in a state where confirmation is not allowed
            if (!order.getOrderStatus().equalsIgnoreCase("PENDING")) {
                throw new IllegalStateException("Order with ID " + orderId + " cannot be confirmed in its current status: " + order.getOrderStatus());
            }

            // 4. Update the order status
            order.setOrderStatus("PENDING_PAYMENT");

            // 5. Save the updated order
            return orderRepository.save(order);
        } else {
            // 6. Handle the case where the order is not found
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
        // Or you could throw an exception if you prefer:
        // return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found."));
    }

    // ... (your placeOrder and calculateDeliveryCost methods) ...
}

