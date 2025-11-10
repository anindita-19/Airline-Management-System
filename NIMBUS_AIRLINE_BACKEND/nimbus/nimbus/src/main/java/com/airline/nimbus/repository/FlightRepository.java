// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/FlightRepository.java
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
