// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/model/Booking.java
// UPDATED: Multi-Seat Support for Connecting Flights
// ============================================

package com.airline.nimbus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private String status; // CONFIRMED, CANCELLED

    @Column(nullable = false)
    private Integer numberOfSeats;

    @Column(nullable = false)
    private Double totalAmount;

    // ✅ CHANGED: seatNumber is now deprecated for connecting flights
    // Kept for backward compatibility with direct flights
    private String seatNumber;

    private String flightClass; // ECONOMY, BUSINESS, FIRST
    private Double luggageWeight;
    private Double luggageCharges;

    // For connecting flights
    private Boolean isConnecting;
    private Long connectingFlightId;

    // ✅ NEW: Multi-seat storage (JSON format: {"flight1": "12A", "flight2": "14C"})
    @Column(length = 1000)
    private String seatNumbers; // Stores multiple seats as JSON string
}