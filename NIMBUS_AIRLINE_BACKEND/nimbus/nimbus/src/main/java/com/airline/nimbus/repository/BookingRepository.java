// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/BookingRepository.java
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Booking;
import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPassenger(Passenger passenger);

    List<Booking> findByFlight(Flight flight);

    List<Booking> findByStatus(String status);
}
