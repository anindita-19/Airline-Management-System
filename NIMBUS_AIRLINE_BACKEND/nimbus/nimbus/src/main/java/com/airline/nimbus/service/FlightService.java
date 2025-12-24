// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/FlightService.java
// UPDATED: Added methods for upcoming flights
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Flight;
import com.airline.nimbus.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public Flight createFlight(Flight flight) {
        flight.setAvailableSeats(flight.getTotalSeats());
        // Set default class seats if not provided
        if (flight.getEconomySeats() == null) {
            flight.setEconomySeats((int)(flight.getTotalSeats() * 0.75));
        }
        if (flight.getBusinessSeats() == null) {
            flight.setBusinessSeats((int)(flight.getTotalSeats() * 0.20));
        }
        if (flight.getFirstClassSeats() == null) {
            flight.setFirstClassSeats((int)(flight.getTotalSeats() * 0.05));
        }
        // Set default prices if not provided
        if (flight.getEconomyPrice() == null) {
            flight.setEconomyPrice(flight.getPrice());
        }
        if (flight.getBusinessPrice() == null) {
            flight.setBusinessPrice(flight.getPrice() * 2);
        }
        if (flight.getFirstClassPrice() == null) {
            flight.setFirstClassPrice(flight.getPrice() * 3);
        }
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, Flight flightDetails) {
        Flight flight = flightRepository.findById(id).orElse(null);
        if (flight != null) {
            flight.setFlightNumber(flightDetails.getFlightNumber());
            flight.setSource(flightDetails.getSource());
            flight.setDestination(flightDetails.getDestination());
            flight.setDepartureDate(flightDetails.getDepartureDate());
            flight.setDepartureTime(flightDetails.getDepartureTime());
            flight.setArrivalTime(flightDetails.getArrivalTime());
            flight.setPrice(flightDetails.getPrice());
            flight.setTotalSeats(flightDetails.getTotalSeats());
            flight.setAirline(flightDetails.getAirline());
            return flightRepository.save(flight);
        }
        return null;
    }

    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Search for direct flights
    public List<Flight> searchFlights(String source, String destination, LocalDate date) {
        return flightRepository.findBySourceAndDestinationAndDepartureDate(
                source, destination, date);
    }

    // NEW: Get upcoming flights (today and future)
    public List<Flight> getUpcomingFlights() {
        return flightRepository.findUpcomingFlights(LocalDate.now());
    }

    // NEW: Get flights for current month
    public List<Flight> getCurrentMonthFlights() {
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return flightRepository.findFlightsBetweenDates(today, endOfMonth);
    }

    // Search for connecting flights
    public List<Map<String, Object>> searchConnectingFlights(
            String source, String destination, LocalDate date) {

        List<Map<String, Object>> connectingFlights = new ArrayList<>();

        // First, get all flights from source on that date
        List<Flight> firstLeg = flightRepository.findBySource(source);

        for (Flight flight1 : firstLeg) {
            // Skip if this goes directly to destination (that's not connecting)
            if (flight1.getDestination().equals(destination)) {
                continue;
            }

            // Skip if not on the requested date
            if (!flight1.getDepartureDate().equals(date)) {
                continue;
            }

            // Find flights from flight1's destination to final destination
            List<Flight> secondLeg = flightRepository.findBySourceAndDestinationAndDepartureDate(
                    flight1.getDestination(), destination, date);

            for (Flight flight2 : secondLeg) {
                // Check if there's enough layover time (1-6 hours)
                Duration layover = Duration.between(
                        flight1.getArrivalTime(),
                        flight2.getDepartureTime()
                );

                long layoverHours = layover.toHours();

                // Only include if layover is reasonable (1-6 hours)
                if (layoverHours >= 1 && layoverHours <= 6) {
                    Map<String, Object> connection = new HashMap<>();
                    connection.put("flight1", flight1);
                    connection.put("flight2", flight2);
                    connection.put("layoverHours", layoverHours);
                    connection.put("totalPrice",
                            flight1.getEconomyPrice() + flight2.getEconomyPrice());
                    connection.put("layoverCity", flight1.getDestination());
                    connectingFlights.add(connection);
                }
            }
        }

        return connectingFlights;
    }

    // Update available seats (for booking)
    public boolean updateAvailableSeats(Long flightId, int seatsBooked) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight != null && flight.getAvailableSeats() >= seatsBooked) {
            flight.setAvailableSeats(flight.getAvailableSeats() - seatsBooked);
            flightRepository.save(flight);
            return true;
        }
        return false;
    }
}