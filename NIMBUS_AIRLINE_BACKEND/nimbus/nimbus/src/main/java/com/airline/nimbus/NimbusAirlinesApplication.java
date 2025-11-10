// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/AirlineApplication.java
// ============================================

package com.airline.nimbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NimbusAirlinesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NimbusAirlinesApplication.class, args);
        System.out.println("Airline Management System Backend is running on http://localhost:8080");
    }
}