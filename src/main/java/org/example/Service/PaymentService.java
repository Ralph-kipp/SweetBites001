package org.example.Service; 

import org.example.Order;
import org.example.Payment;
import org.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPaymentForOrder(Order order, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(order.getTotalCost()); 
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long paymentId, String newStatus, String transactionId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> {
                    payment.setPaymentStatus(newStatus);
                    if (transactionId != null) {
                        payment.setTransactionId(transactionId);
                    }
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new RuntimeException("Payment with ID " + paymentId + " not found"));
    }

}
