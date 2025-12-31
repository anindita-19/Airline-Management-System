// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/config/SecurityConfig.java
// FIXED: Added authentication endpoints to public access
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
                                // Health checks
                                "/health",
                                "/ping",

                                // 🔑 AUTHENTICATION ENDPOINTS (CRITICAL!)
                                "/api/passengers/login",
                                "/api/passengers/signup",
                                "/api/admin/login",
                                "/api/admin/create",

                                // Flight search (read-only)
                                "/api/flights/search",
                                "/api/flights/search/connecting",
                                "/api/flights/upcoming",
                                "/api/flights/current-month",
                                "/api/flights",
                                "/api/flights/**",
                                  "/api/seats/**"
                        ).permitAll()

                        // 🔒 PROTECTED ENDPOINTS - Login required
                        .anyRequest().authenticated()
                )

                // Use stateless sessions (REST API pattern)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Disable default login page (we have custom frontend)
                .formLogin(form -> form.disable())

                // Disable HTTP Basic auth popup
                .httpBasic(basic -> basic.disable())

                // Allow cross-origin requests (already configured in CorsConfig)
                .cors(cors -> {});

        System.out.println("✅ Security configured:");
        System.out.println("   PUBLIC: /health, /ping");
        System.out.println("   PUBLIC: /api/passengers/login, /api/passengers/signup");
        System.out.println("   PUBLIC: /api/admin/login, /api/admin/create");
        System.out.println("   PUBLIC: /api/flights/** (all flight endpoints)");
        System.out.println("   PROTECTED: All other endpoints (bookings, payments, etc.)");

        return http.build();
    }
}