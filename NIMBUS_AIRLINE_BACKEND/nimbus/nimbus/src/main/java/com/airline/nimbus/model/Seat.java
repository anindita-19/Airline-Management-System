// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/model/Seat.java
// CREATE NEW FILE - This is a NEW model
// ============================================

package com.airline.nimbus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private String seatNumber; // Example: 12A, 15B

    @Column(nullable = false)
    private String seatClass; // ECONOMY, BUSINESS, FIRST

    @Column(nullable = false)
    private Boolean isAvailable;
}