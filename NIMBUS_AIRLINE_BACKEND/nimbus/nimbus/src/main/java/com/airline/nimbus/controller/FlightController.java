// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/FlightController.java
// UPDATED: Added endpoints for upcoming flights
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Flight;
import com.airline.nimbus.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        System.out.println("=== GET ALL FLIGHTS REQUEST ===");
        List<Flight> flights = flightService.getAllFlights();
        System.out.println("✅ Returning " + flights.size() + " flights");
        return ResponseEntity.ok(flights);
    }

    // NEW: Get upcoming flights (today and future)
    @GetMapping("/upcoming")
    public ResponseEntity<List<Flight>> getUpcomingFlights() {
        System.out.println("=== GET UPCOMING FLIGHTS ===");
        List<Flight> flights = flightService.getUpcomingFlights();
        System.out.println("✅ Found " + flights.size() + " upcoming flights");
        return ResponseEntity.ok(flights);
    }

    // NEW: Get current month flights
    @GetMapping("/current-month")
    public ResponseEntity<List<Flight>> getCurrentMonthFlights() {
        System.out.println("=== GET CURRENT MONTH FLIGHTS ===");
        List<Flight> flights = flightService.getCurrentMonthFlights();
        System.out.println("✅ Found " + flights.size() + " flights this month");
        return ResponseEntity.ok(flights);
    }

    // IMPORTANT: /search MUST come BEFORE /{id}
    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {

        System.out.println("=== DIRECT FLIGHT SEARCH ===");
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Date: " + date);

        try {
            LocalDate departureDate = LocalDate.parse(date);
            List<Flight> flights = flightService.searchFlights(
                    source.toUpperCase(),
                    destination.toUpperCase(),
                    departureDate
            );

            System.out.println("✅ Found " + flights.size() + " direct flights");
            return ResponseEntity.ok(flights);

        } catch (Exception e) {
            System.out.println("❌ Error searching flights: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Search for connecting flights
    @GetMapping("/search/connecting")
    public ResponseEntity<List<Map<String, Object>>> searchConnectingFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {

        System.out.println("=== CONNECTING FLIGHT SEARCH ===");
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Date: " + date);

        try {
            LocalDate departureDate = LocalDate.parse(date);
            List<Map<String, Object>> connectingFlights =
                    flightService.searchConnectingFlights(
                            source.toUpperCase(),
                            destination.toUpperCase(),
                            departureDate
                    );

            System.out.println("✅ Found " + connectingFlights.size() + " connecting flight options");
            return ResponseEntity.ok(connectingFlights);

        } catch (Exception e) {
            System.out.println("❌ Error searching connecting flights: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        System.out.println("=== GET FLIGHT BY ID: " + id + " ===");
        Flight flight = flightService.getFlightById(id);
        if (flight != null) {
            System.out.println("✅ Flight found: " + flight.getFlightNumber());
            return ResponseEntity.ok(flight);
        }
        System.out.println("❌ Flight not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        System.out.println("=== CREATE FLIGHT REQUEST ===");
        System.out.println("Flight Number: " + flight.getFlightNumber());

        try {
            Flight created = flightService.createFlight(flight);
            System.out.println("✅ Flight created with ID: " + created.getId());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(
            @PathVariable Long id,
            @RequestBody Flight flight) {

        System.out.println("=== UPDATE FLIGHT: " + id + " ===");

        try {
            Flight updated = flightService.updateFlight(id, flight);
            if (updated != null) {
                System.out.println("✅ Flight updated");
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        System.out.println("=== DELETE FLIGHT: " + id + " ===");

        if (flightService.deleteFlight(id)) {
            System.out.println("✅ Flight deleted");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}