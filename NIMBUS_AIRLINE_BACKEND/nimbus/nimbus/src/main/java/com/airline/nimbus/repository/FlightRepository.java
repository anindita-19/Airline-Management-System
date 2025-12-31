// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/repository/FlightRepository.java
// FIXED: Changed from JPQL to Native SQL queries for date arithmetic
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

    // ✅ FIXED: Using native SQL query instead of JPQL for date arithmetic
    @Query(value = "SELECT * FROM flights f WHERE f.departure_date >= CURDATE() " +
            "AND f.departure_date <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) " +
            "ORDER BY f.departure_date ASC, f.departure_time ASC",
            nativeQuery = true)
    List<Flight> findUpcomingFlights();

    // ✅ FIXED: Using native SQL query with parameters
    @Query(value = "SELECT * FROM flights f WHERE f.departure_date >= :startDate " +
            "AND f.departure_date <= :endDate " +
            "ORDER BY f.departure_date ASC, f.departure_time ASC",
            nativeQuery = true)
    List<Flight> findFlightsBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // ✅ FIXED: Using native SQL query with dynamic days parameter
    @Query(value = "SELECT * FROM flights f WHERE f.departure_date >= CURDATE() " +
            "AND f.departure_date <= DATE_ADD(CURDATE(), INTERVAL :days DAY) " +
            "ORDER BY f.departure_date ASC, f.departure_time ASC",
            nativeQuery = true)
    List<Flight> findFlightsForNextDays(@Param("days") int days);
}