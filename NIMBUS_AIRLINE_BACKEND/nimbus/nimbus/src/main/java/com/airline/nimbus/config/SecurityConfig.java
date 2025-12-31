// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/config/SecurityConfig.java
// PURPOSE: Configure which endpoints are public vs protected
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

                // Configure which endpoints are public vs protected
                .authorizeHttpRequests(auth -> auth
                        // ✅ PUBLIC ENDPOINTS - No login required
                        .requestMatchers(
                                "/health",                          // Health check for UptimeRobot
                                "/ping",                            // Alternative health check
                                "/api/flights/search",              // Direct flight search
                                "/api/flights/search/connecting",   // Connecting flight search
                                "/api/flights/upcoming",            // Browse upcoming flights
                                "/api/flights/current-month",       // Current month flights
                                "/api/flights",                     // View all flights (read-only)
                                "/api/flights/{id}"                 // View single flight details
                        ).permitAll()

                        // 🔒 PROTECTED ENDPOINTS - Login required
                        .anyRequest().authenticated()           // Everything else needs auth
                )

                // Use stateless sessions (REST API pattern)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Disable default login page (we have custom frontend)
                .formLogin(form -> form.disable())

                // Allow cross-origin requests (already configured in CorsConfig)
                .cors(cors -> {});

        System.out.println("✅ Security configured:");
        System.out.println("   - Public: /health, /api/flights/search, /api/flights/upcoming");
        System.out.println("   - Protected: All other endpoints");

        return http.build();
    }
}