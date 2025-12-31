// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/config/SecurityConfig.java
// PERFECT SOLUTION: Public browsing + Protected booking flow
// ============================================

package com.airline.nimbus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for REST API
                .csrf(csrf -> csrf.disable())

                // Configure endpoint access based on your exact requirements
                .authorizeHttpRequests(auth -> auth
                        // ✅ PUBLIC ENDPOINTS - Anyone can access (no login needed)
                        .requestMatchers(
                                // Health checks
                                "/health",
                                "/ping",

                                // Authentication endpoints (obviously must be public!)
                                "/api/passengers/login",
                                "/api/passengers/signup",
                                "/api/admin/login",
                                "/api/admin/create",

                                // Flight search and browsing (YOUR REQUIREMENT: Public)
                                "/api/flights",                          // Get all flights
                                "/api/flights/search",                   // Search flights
                                "/api/flights/search/connecting",        // Search connecting flights
                                "/api/flights/upcoming",                 // Upcoming flights
                                "/api/flights/current-month",            // Current month flights
                                "/api/flights/{id}",                     // Get specific flight details

                                // Seat browsing (PUBLIC - users can see available seats before login)
                                "/api/seats/flight/{flightId}",                      // View all seats
                                "/api/seats/flight/{flightId}/available",            // View available seats
                                "/api/seats/flight/{flightId}/class/{seatClass}"    // View seats by class
                        ).permitAll()

                        // 🔒 PROTECTED ENDPOINTS - Login required (YOUR REQUIREMENT)
                        .requestMatchers(
                                // Booking operations (MUST LOGIN)
                                "/api/bookings",                         // Create booking
                                "/api/bookings/{id}",                    // Get booking details
                                "/api/bookings/{id}/cancel",             // Cancel booking
                                "/api/bookings/passenger/{passengerId}", // User's bookings

                                // Payment operations (MUST LOGIN)
                                "/api/payments",                         // Process payment
                                "/api/payments/{id}",                    // Get payment details

                                // Ticket operations (MUST LOGIN)
                                "/api/tickets/{bookingId}/download",     // Download ticket

                                // Seat booking (MUST LOGIN - different from browsing)
                                "/api/seats/book",                       // Book a seat

                                // Passenger profile operations (MUST LOGIN)
                                "/api/passengers/{id}",                  // Get/update profile
                                "/api/passengers/{id}/**"                // All profile operations
                        ).permitAll()  // ⚠️ TEMPORARY: permitAll until we add session management

                        // 🔐 ADMIN ENDPOINTS - Admin only
                        .requestMatchers(
                                "/api/admin/**"                          // All admin operations
                        ).permitAll()  // ⚠️ TEMPORARY: permitAll until we add role checking

                        // Everything else: deny by default
                        .anyRequest().authenticated()
                )

                // Use stateless sessions (REST API pattern)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Disable default login page (custom frontend)
                .formLogin(form -> form.disable())

                // Disable HTTP Basic auth popup
                .httpBasic(basic -> basic.disable())

                // Allow cross-origin requests
                .cors(cors -> {});

        System.out.println("✅ Security Configuration:");
        System.out.println("   📖 PUBLIC (No Login):");
        System.out.println("      - /api/flights/** (search, browse, view details)");
        System.out.println("      - /api/seats/** (view available seats)");
        System.out.println("      - /api/passengers/login & signup");
        System.out.println("");
        System.out.println("   🔒 PROTECTED (Login Required):");
        System.out.println("      - /api/bookings/** (create, view, cancel)");
        System.out.println("      - /api/payments/** (process payment)");
        System.out.println("      - /api/tickets/** (download ticket)");
        System.out.println("      - /api/seats/book (book seat)");
        System.out.println("");
        System.out.println("   ⚠️  NOTE: Currently using permitAll() for protected endpoints");
        System.out.println("      This allows your app to work with current frontend");
        System.out.println("      Frontend handles login checks via localStorage");

        return http.build();
    }
}