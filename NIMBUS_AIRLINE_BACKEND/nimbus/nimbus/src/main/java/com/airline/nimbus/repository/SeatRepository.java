// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/SeatRepository.java
// CREATE NEW FILE
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats for a specific flight
    List<Seat> findByFlightId(Long flightId);

    // Find available seats for a flight
    List<Seat> findByFlightIdAndIsAvailable(Long flightId, Boolean isAvailable);

    // Find seats by flight and class
    List<Seat> findByFlightIdAndSeatClass(Long flightId, String seatClass);

    // Find a specific seat
    Seat findByFlightIdAndSeatNumber(Long flightId, String seatNumber);
}
