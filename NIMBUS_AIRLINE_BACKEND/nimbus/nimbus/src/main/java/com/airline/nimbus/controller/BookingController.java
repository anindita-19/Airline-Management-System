// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/BookingController.java
// UPDATED: Multi-Seat Support
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Booking;
import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.model.Flight;
import com.airline.nimbus.service.BookingService;
import com.airline.nimbus.service.PassengerService;
import com.airline.nimbus.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private FlightService flightService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        System.out.println("=== GET ALL BOOKINGS REQUEST ===");
        List<Booking> bookings = bookingService.getAllBookings();
        System.out.println("✅ Returning " + bookings.size() + " bookings");
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        System.out.println("=== GET BOOKING BY ID: " + id + " ===");
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            System.out.println("✅ Booking found");
            return ResponseEntity.ok(booking);
        }
        System.out.println("❌ Booking not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> bookingData) {
        System.out.println("=== CREATE BOOKING REQUEST ===");
        System.out.println("Booking Data Received: " + bookingData);

        try {
            Long passengerId = Long.valueOf(bookingData.get("passengerId").toString());
            Long flightId = Long.valueOf(bookingData.get("flightId").toString());
            Integer numberOfSeats = Integer.valueOf(bookingData.get("numberOfSeats").toString());

            String flightClass = bookingData.get("flightClass") != null ?
                    bookingData.get("flightClass").toString() : "ECONOMY";
            Double luggageWeight = bookingData.get("luggageWeight") != null ?
                    Double.valueOf(bookingData.get("luggageWeight").toString()) : 0.0;
            Boolean isConnecting = bookingData.get("isConnecting") != null ?
                    Boolean.valueOf(bookingData.get("isConnecting").toString()) : false;
            Long connectingFlightId = bookingData.get("connectingFlightId") != null ?
                    Long.valueOf(bookingData.get("connectingFlightId").toString()) : null;

            // ✅ NEW: Handle multiple seats
            String seatNumbersJson = null;
            String legacySeatNumber = null;

            if (isConnecting && bookingData.containsKey("seatNumbers")) {
                // Multi-seat booking (new format)
                @SuppressWarnings("unchecked")
                Map<String, String> seatMap = (Map<String, String>) bookingData.get("seatNumbers");
                seatNumbersJson = objectMapper.writeValueAsString(seatMap);
                System.out.println("✅ Multi-seat booking: " + seatNumbersJson);
            } else if (bookingData.get("seatNumber") != null) {
                // Single seat booking (legacy/direct flight)
                legacySeatNumber = bookingData.get("seatNumber").toString();
                System.out.println("✅ Single seat booking: " + legacySeatNumber);
            }

            System.out.println("Passenger ID: " + passengerId);
            System.out.println("Flight ID: " + flightId);
            System.out.println("Seats: " + numberOfSeats);
            System.out.println("Flight Class: " + flightClass);
            System.out.println("Luggage: " + luggageWeight + "kg");
            System.out.println("Is Connecting: " + isConnecting);
            System.out.println("Connecting Flight ID: " + connectingFlightId);

            Passenger passenger = passengerService.getPassengerById(passengerId);
            Flight flight = flightService.getFlightById(flightId);

            if (passenger == null || flight == null) {
                System.out.println("❌ Invalid passenger or flight");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid passenger or flight");
                return ResponseEntity.badRequest().body(error);
            }

            Booking booking = new Booking();
            booking.setPassenger(passenger);
            booking.setFlight(flight);
            booking.setNumberOfSeats(numberOfSeats);
            booking.setSeatNumber(legacySeatNumber); // For backward compatibility
            booking.setSeatNumbers(seatNumbersJson); // ✅ NEW: Multi-seat storage
            booking.setFlightClass(flightClass);
            booking.setLuggageWeight(luggageWeight);
            booking.setIsConnecting(isConnecting);
            booking.setConnectingFlightId(connectingFlightId);

            Booking created = bookingService.createBooking(booking);
            if (created != null) {
                System.out.println("✅ Booking created: ID=" + created.getId() +
                        ", Class=" + created.getFlightClass() +
                        ", Total=₹" + created.getTotalAmount());
                return ResponseEntity.ok(created);
            }

            System.out.println("❌ Not enough seats");
            Map<String, String> error = new HashMap<>();
            error.put("message", "Not enough seats available");
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid booking data: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        System.out.println("=== CANCEL BOOKING REQUEST ===");
        System.out.println("Booking ID: " + id);

        Booking cancelled = bookingService.cancelBooking(id);
        if (cancelled != null) {
            System.out.println("✅ Booking cancelled successfully");
            return ResponseEntity.ok(cancelled);
        }

        System.out.println("❌ Cannot cancel booking");
        Map<String, String> error = new HashMap<>();
        error.put("message", "Cannot cancel booking");
        return ResponseEntity.badRequest().body(error);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Booking>> getBookingsByPassenger(@PathVariable Long passengerId) {
        System.out.println("=== GET BOOKINGS BY PASSENGER: " + passengerId + " ===");
        Passenger passenger = passengerService.getPassengerById(passengerId);
        if (passenger != null) {
            List<Booking> bookings = bookingService.getBookingsByPassenger(passenger);
            System.out.println("✅ Found " + bookings.size() + " bookings");
            return ResponseEntity.ok(bookings);
        }
        System.out.println("❌ Passenger not found");
        return ResponseEntity.notFound().build();
    }
}