// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/config/CorsConfig.java
// UPDATED TO ALLOW MULTIPLE ORIGINS
// ============================================

package com.airline.nimbus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // Allow BOTH localhost and 127.0.0.1 on common ports
                        .allowedOrigins(
        "http://localhost:5500",
        "http://localhost:5501",
        "http://localhost:5502",
        "http://127.0.0.1:5500",
        "http://127.0.0.1:5501",
        "http://127.0.0.1:5502",
        "https://airline-backend-i4wj.onrender.com",
        "https://*.netlify.app"
)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);

                System.out.println("✅ CORS configured for multiple origins");
            }
        };
    }
}