// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/model/Flight.java
// COMPLETE VERSION WITH CLASS PRICES
// ============================================

package com.airline.nimbus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    private String airline;

    // NEW: Class-based seating
    private Integer economySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;

    // NEW: Class-based pricing
    private Double economyPrice;
    private Double businessPrice;
    private Double firstClassPrice;
}