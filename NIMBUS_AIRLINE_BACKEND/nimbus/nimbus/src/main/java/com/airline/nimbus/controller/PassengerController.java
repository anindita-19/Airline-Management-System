// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/PassengerController.java
// COMPLETE VERSION - All endpoints included
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "http://localhost:5500")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger != null) {
            return ResponseEntity.ok(passenger);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Passenger passenger) {
        System.out.println("=== SIGNUP REQUEST RECEIVED ===");
        System.out.println("Email: " + passenger.getEmail());
        System.out.println("Name: " + passenger.getFirstName() + " " + passenger.getLastName());

        try {
            Passenger created = passengerService.createPassenger(passenger);
            if (created != null) {
                System.out.println("✅ Passenger created successfully with ID: " + created.getId());
                return ResponseEntity.ok(created);
            } else {
                System.out.println("❌ Email already exists: " + passenger.getEmail());
                Map<String, String> error = new HashMap<>();
                error.put("message", "Email already exists");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            System.out.println("❌ Error during signup: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Signup failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        System.out.println("=== LOGIN REQUEST RECEIVED ===");
        String email = credentials.get("email");
        String password = credentials.get("password");
        System.out.println("Email: " + email);
        System.out.println("Password length: " + (password != null ? password.length() : 0));

        try {
            Passenger passenger = passengerService.login(email, password);
            if (passenger != null) {
                System.out.println("✅ Login successful for: " + passenger.getFirstName() + " " + passenger.getLastName());
                return ResponseEntity.ok(passenger);
            } else {
                System.out.println("❌ Invalid credentials for email: " + email);
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid email or password");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            System.out.println("❌ Error during login: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(
            @PathVariable Long id,
            @RequestBody Passenger passenger) {
        Passenger updated = passengerService.updatePassenger(id, passenger);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        if (passengerService.deletePassenger(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}