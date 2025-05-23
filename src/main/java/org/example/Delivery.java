package org.example;

// Delivery.java
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String deliveryZone; // near, mid, far
    private LocalDateTime scheduledDeliveryTime;
    private String deliveryStatus; // Scheduled, Out for Delivery, Delivered
}