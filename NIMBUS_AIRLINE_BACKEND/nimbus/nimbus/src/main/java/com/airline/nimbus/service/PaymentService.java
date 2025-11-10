// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/PaymentService.java
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Payment;
import com.airline.nimbus.model.Booking;
import com.airline.nimbus.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        // Simulate payment processing
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        // In real scenario, integrate with payment gateway
        // For now, we'll simulate success
        payment.setPaymentStatus("SUCCESS");

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment getPaymentByBooking(Booking booking) {
        return paymentRepository.findByBooking(booking);
    }
}
