// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/model/Payment.java
// ============================================

package com.airline.nimbus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentMethod; // CARD, UPI, NET_BANKING

    @Column(nullable = false)
    private String paymentStatus; // SUCCESS, FAILED, PENDING

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    private String transactionId;
}

