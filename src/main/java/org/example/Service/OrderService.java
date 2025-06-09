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
        
        Customer customerDetails = orderRequest.getCustomer();

        
        Optional<Customer> existingCustomer = customerRepository.findByPhoneNumber(customerDetails.getPhoneNumber());
        Customer customer = existingCustomer.orElseGet(() -> customerRepository.save(customerDetails));

       
        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliveryDate(LocalDate.parse(orderRequest.getDeliveryDate()));
        order.setDeliveryZone(orderRequest.getDeliveryZone()); 
        order.setOrderStatus("PENDING"); 
        order.setProducts(new ArrayList<>());

        double totalProductCost = 0.0;
        List<OrderItemRequest> orderItems = orderRequest.getOrderItems();


        for (OrderItemRequest itemRequest : orderItems) {
            Long productId = itemRequest.getProductId();
            int quantity = itemRequest.getQuantity();
            Optional<Product> productOptional = productRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                order.getProducts().add(product);
                totalProductCost += product.getPrice() * quantity;
               
            } else {
                throw new RuntimeException("Product with ID " + productId + " not found.");
            }
        }

      
        double deliveryCost = calculateDeliveryCost(orderRequest.getDeliveryZone());

       
        double totalCost = totalProductCost + deliveryCost;
        order.setTotalCost(totalCost);

        
        Order savedOrder = orderRepository.save(order);

   
        return savedOrder;
    }

    
    private double calculateDeliveryCost(String deliveryZone) {
       
        switch (deliveryZone.toLowerCase()) {
            case "near":
                return 50.0; 
            case "mid":
                return 100.0;
            case "far":
                return 150.0; 
            default:
                return 0.0; 
        }
    }

    public Order confirmOrder(Long orderId) {
      
        Optional<Order> orderOptional = orderRepository.findById(orderId);

      
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

          
            if (!order.getOrderStatus().equalsIgnoreCase("PENDING")) {
                throw new IllegalStateException("Order with ID " + orderId + " cannot be confirmed in its current status: " + order.getOrderStatus());
            }

           
            order.setOrderStatus("PENDING_PAYMENT");

            
            return orderRepository.save(order);
        } else {
           
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
        
    }

    
}

