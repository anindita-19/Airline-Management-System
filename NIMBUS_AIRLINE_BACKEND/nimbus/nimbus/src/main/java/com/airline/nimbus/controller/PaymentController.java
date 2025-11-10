// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/PaymentController.java
// UPDATED with better logging
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Payment;
import com.airline.nimbus.model.Booking;
import com.airline.nimbus.service.PaymentService;
import com.airline.nimbus.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> paymentData) {
        System.out.println("=== PROCESS PAYMENT REQUEST ===");

        try {
            Long bookingId = Long.valueOf(paymentData.get("bookingId").toString());
            Double amount = Double.valueOf(paymentData.get("amount").toString());
            String paymentMethod = paymentData.get("paymentMethod").toString();

            System.out.println("Booking ID: " + bookingId);
            System.out.println("Amount: ₹" + amount);
            System.out.println("Payment Method: " + paymentMethod);

            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                System.out.println("❌ Booking not found");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Booking not found");
                return ResponseEntity.badRequest().body(error);
            }

            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);

            Payment processed = paymentService.processPayment(payment);
            System.out.println("✅ Payment processed. Transaction ID: " + processed.getTransactionId());
            return ResponseEntity.ok(processed);

        } catch (Exception e) {
            System.out.println("❌ Payment processing failed: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Payment processing failed");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        System.out.println("=== GET PAYMENT BY ID: " + id + " ===");
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            System.out.println("✅ Payment found");
            return ResponseEntity.ok(payment);
        }
        System.out.println("❌ Payment not found");
        return ResponseEntity.notFound().build();
    }
}
