package org.example.controller; // Or your actual controller package

import org.example.Order;
import org.example.Service.OrderService;
import org.example.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.dto.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order placedOrder = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId) {
        try {
            Order confirmedOrder = orderService.confirmOrder(orderId);
            return new ResponseEntity<>(confirmedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (e instanceof org.springframework.dao.EmptyResultDataAccessException || e.getMessage().contains("not found")) {
                status = HttpStatus.NOT_FOUND;
            } else if (e instanceof IllegalStateException) {
                status = HttpStatus.BAD_REQUEST;
            }
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }
}