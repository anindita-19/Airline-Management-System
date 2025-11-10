// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/SeatController.java
// CREATE NEW FILE
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Seat;
import com.airline.nimbus.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class SeatController {

    @Autowired
    private SeatService seatService;

    // Get all seats for a flight
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Seat>> getSeatsByFlight(@PathVariable Long flightId) {
        System.out.println("=== GET SEATS FOR FLIGHT: " + flightId + " ===");
        List<Seat> seats = seatService.getSeatsByFlight(flightId);
        System.out.println("✅ Returning " + seats.size() + " seats");
        return ResponseEntity.ok(seats);
    }

    // Get available seats for a flight
    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long flightId) {
        System.out.println("=== GET AVAILABLE SEATS FOR FLIGHT: " + flightId + " ===");
        List<Seat> seats = seatService.getAvailableSeats(flightId);
        System.out.println("✅ Found " + seats.size() + " available seats");
        return ResponseEntity.ok(seats);
    }

    // Get seats by class
    @GetMapping("/flight/{flightId}/class/{seatClass}")
    public ResponseEntity<List<Seat>> getSeatsByClass(
            @PathVariable Long flightId,
            @PathVariable String seatClass) {
        System.out.println("=== GET SEATS BY CLASS: " + seatClass + " for flight " + flightId + " ===");
        List<Seat> seats = seatService.getSeatsByClass(flightId, seatClass);
        System.out.println("✅ Found " + seats.size() + " seats");
        return ResponseEntity.ok(seats);
    }

    // Book a seat
    @PostMapping("/book")
    public ResponseEntity<?> bookSeat(@RequestBody Map<String, Object> seatData) {
        System.out.println("=== BOOK SEAT REQUEST ===");

        try {
            Long flightId = Long.valueOf(seatData.get("flightId").toString());
            String seatNumber = seatData.get("seatNumber").toString();

            System.out.println("Flight ID: " + flightId);
            System.out.println("Seat Number: " + seatNumber);

            boolean booked = seatService.bookSeat(flightId, seatNumber);

            if (booked) {
                System.out.println("✅ Seat booked successfully");
                Map<String, String> response = new HashMap<>();
                response.put("message", "Seat booked successfully");
                response.put("seatNumber", seatNumber);
                return ResponseEntity.ok(response);
            } else {
                System.out.println("❌ Seat not available");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Seat not available");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            System.out.println("❌ Error booking seat: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error booking seat");
            return ResponseEntity.badRequest().body(error);
        }
    }
}