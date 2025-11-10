// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/SeatService.java
// CREATE NEW FILE
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Seat;
import com.airline.nimbus.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    // Get all seats for a flight
    public List<Seat> getSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }

    // Get available seats for a flight
    public List<Seat> getAvailableSeats(Long flightId) {
        return seatRepository.findByFlightIdAndIsAvailable(flightId, true);
    }

    // Get seats by class
    public List<Seat> getSeatsByClass(Long flightId, String seatClass) {
        return seatRepository.findByFlightIdAndSeatClass(flightId, seatClass);
    }

    // Book a seat (mark as unavailable)
    public boolean bookSeat(Long flightId, String seatNumber) {
        Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber);
        if (seat != null && seat.getIsAvailable()) {
            seat.setIsAvailable(false);
            seatRepository.save(seat);
            return true;
        }
        return false;
    }

    // Release a seat (mark as available)
    public boolean releaseSeat(Long flightId, String seatNumber) {
        Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber);
        if (seat != null) {
            seat.setIsAvailable(true);
            seatRepository.save(seat);
            return true;
        }
        return false;
    }
}
