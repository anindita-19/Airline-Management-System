// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/controller/HealthController.java
// PURPOSE: Lightweight health check to prevent Render cold starts
// NO DATABASE, NO AUTHENTICATION, NO SERVICES
// ============================================

package com.airline.nimbus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Allow from anywhere (it's just a health check)
public class HealthController {

    // ✅ Simple health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Nimbus Airlines Backend");
        response.put("timestamp", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));
        response.put("message", "Service is running");

        return ResponseEntity.ok(response);
    }

    // ✅ Alternative endpoint (some monitoring tools prefer /ping)
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}