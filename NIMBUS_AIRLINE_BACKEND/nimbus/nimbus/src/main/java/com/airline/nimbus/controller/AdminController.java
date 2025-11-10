// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/AdminController.java
// REPLACE existing AdminController.java with this
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.service.AdminService;
import com.airline.nimbus.model.Admin;
import com.airline.nimbus.model.Flight;
import com.airline.nimbus.model.Booking;
import com.airline.nimbus.model.Passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> credentials) {
        System.out.println("=== ADMIN LOGIN REQUEST ===");
        String email = credentials.get("email");
        String password = credentials.get("password");
        System.out.println("Email: " + email);

        try {
            // Try to login using database
            Admin admin = adminService.login(email, password);

            if (admin != null) {
                System.out.println("✅ Admin login successful from database: " + admin.getEmail());
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("username", admin.getUsername());
                response.put("email", admin.getEmail());
                response.put("role", "ADMIN");
                response.put("id", admin.getId());
                return ResponseEntity.ok(response);
            }


            System.out.println("❌ Invalid admin credentials");
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid username or password");
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            System.out.println("❌ Error during admin login: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    // Create admin (for initial setup)
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        System.out.println("=== CREATE ADMIN REQUEST ===");
        System.out.println("Email: " + admin.getEmail());

        try {
            Admin created = adminService.createAdmin(admin);
            if (created != null) {
                System.out.println("✅ Admin created successfully");
                return ResponseEntity.ok(created);
            } else {
                System.out.println("❌ Username already exists");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Username already exists");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating admin: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error creating admin");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(adminService.getAllFlights());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(adminService.getAllPassengers());
    }
}