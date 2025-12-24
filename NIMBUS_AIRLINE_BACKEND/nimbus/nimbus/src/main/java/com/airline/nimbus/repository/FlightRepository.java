// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/FlightRepository.java
// UPDATED: Added methods for upcoming flights
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findBySourceAndDestinationAndDepartureDate(
            String source, String destination, LocalDate departureDate);

    List<Flight> findBySource(String source);

    List<Flight> findByDestination(String destination);

    Flight findByFlightNumber(String flightNumber);

    // NEW: Get all upcoming flights (today and future)
    @Query("SELECT f FROM Flight f WHERE f.departureDate >= :today ORDER BY f.departureDate ASC, f.departureTime ASC")
    List<Flight> findUpcomingFlights(LocalDate today);

    // NEW: Get flights for a specific date range
    @Query("SELECT f FROM Flight f WHERE f.departureDate BETWEEN :startDate AND :endDate ORDER BY f.departureDate ASC, f.departureTime ASC")
    List<Flight> findFlightsBetweenDates(LocalDate startDate, LocalDate endDate);
}