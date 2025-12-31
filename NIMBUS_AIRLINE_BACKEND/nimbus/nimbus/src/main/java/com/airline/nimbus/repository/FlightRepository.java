// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/FlightRepository.java
// UPDATED: Changed to rolling 30-day date logic
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // ✅ UPDATED: Get upcoming flights (today + next 30 days)
    // This works across month and year boundaries
    @Query("SELECT f FROM Flight f WHERE f.departureDate >= CURRENT_DATE " +
            "AND f.departureDate <= DATE_ADD(CURRENT_DATE, 30) " +
            "ORDER BY f.departureDate ASC, f.departureTime ASC")
    List<Flight> findUpcomingFlights();

    // ✅ UPDATED: Get flights within rolling date range
    @Query("SELECT f FROM Flight f WHERE f.departureDate >= :startDate " +
            "AND f.departureDate <= :endDate " +
            "ORDER BY f.departureDate ASC, f.departureTime ASC")
    List<Flight> findFlightsBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // ✅ NEW: Get flights for next N days (configurable)
    @Query("SELECT f FROM Flight f WHERE f.departureDate >= CURRENT_DATE " +
            "AND f.departureDate <= DATE_ADD(CURRENT_DATE, :days) " +
            "ORDER BY f.departureDate ASC, f.departureTime ASC")
    List<Flight> findFlightsForNextDays(@Param("days") int days);
}