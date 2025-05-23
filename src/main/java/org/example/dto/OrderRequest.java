package org.example.dto;// Or com.sweetbiteorders.dto
import org.example.Customer;
import java.util.List;

public class OrderRequest {
    private Customer customer;
    private List<OrderItemRequest> orderItems;
    private String deliveryDate;
    private String deliveryZone;

    // Constructors (optional, but good practice)
    public OrderRequest() {
    }

    public OrderRequest(Customer customer, List<OrderItemRequest> orderItems, String deliveryDate, String deliveryZone) {
        this.customer = customer;
        this.orderItems = orderItems;
        this.deliveryDate = deliveryDate;
        this.deliveryZone = deliveryZone;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }

    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
    }
}