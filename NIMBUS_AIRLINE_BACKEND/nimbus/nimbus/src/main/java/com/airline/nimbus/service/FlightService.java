// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/nimbus/service/FlightService.java
// UPDATED: Added updateAvailableSeats method
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Flight;
import com.airline.nimbus.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // ✅ UPDATED: Get upcoming flights (today + next 30 days)
    public List<Flight> getUpcomingFlights() {
        System.out.println("📅 Getting flights from today to next 30 days");
        return flightRepository.findUpcomingFlights();
    }

    // ✅ UPDATED: Renamed from getCurrentMonthFlights to getNext30DaysFlights
    public List<Flight> getCurrentMonthFlights() {
        // Keep method name for backward compatibility
        // But internally use 30-day rolling window
        System.out.println("📅 Getting flights for next 30 days (rolling window)");
        return flightRepository.findUpcomingFlights();
    }

    // ✅ NEW: Get flights for custom number of days
    public List<Flight> getFlightsForNextDays(int days) {
        System.out.println("📅 Getting flights for next " + days + " days");
        return flightRepository.findFlightsForNextDays(days);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public List<Flight> searchFlights(String source, String destination, LocalDate date) {
        System.out.println("🔍 Searching direct flights: " + source + " → " + destination + " on " + date);
        return flightRepository.findBySourceAndDestinationAndDepartureDate(
                source, destination, date);
    }

    public List<Map<String, Object>> searchConnectingFlights(
            String source, String destination, LocalDate date) {

        System.out.println("🔍 Searching connecting flights: " + source + " → " + destination);

        List<Map<String, Object>> connectingOptions = new ArrayList<>();

        // Find all flights from source on the given date
        List<Flight> firstLegs = flightRepository.findBySource(source).stream()
                .filter(f -> f.getDepartureDate().equals(date))
                .toList();

        System.out.println("   Found " + firstLegs.size() + " potential first legs");

        // For each first leg, find connecting flights
        for (Flight firstFlight : firstLegs) {
            String layoverCity = firstFlight.getDestination();

            // Skip if this is already a direct flight
            if (layoverCity.equals(destination)) {
                continue;
            }

            // Find flights from layover city to final destination
            List<Flight> secondLegs = flightRepository
                    .findBySourceAndDestinationAndDepartureDate(
                            layoverCity, destination, date);

            for (Flight secondFlight : secondLegs) {
                // Check if there's enough time for connection (1-8 hours)
                if (isValidConnection(firstFlight, secondFlight)) {
                    long layoverMinutes = calculateLayoverMinutes(firstFlight, secondFlight);
                    double layoverHours = Math.round(layoverMinutes / 60.0 * 10.0) / 10.0;

                    Map<String, Object> connection = new HashMap<>();
                    connection.put("flight1", firstFlight);
                    connection.put("flight2", secondFlight);
                    connection.put("layoverCity", layoverCity);
                    connection.put("layoverHours", layoverHours);
                    connection.put("totalPrice",
                            firstFlight.getEconomyPrice() + secondFlight.getEconomyPrice());

                    connectingOptions.add(connection);
                }
            }
        }

        System.out.println("   ✅ Found " + connectingOptions.size() + " connecting options");
        return connectingOptions;
    }

    private boolean isValidConnection(Flight first, Flight second) {
        LocalTime firstArrival = first.getArrivalTime();
        LocalTime secondDeparture = second.getDepartureTime();

        long layoverMinutes = calculateLayoverMinutes(first, second);

        // Valid connection: 60 minutes to 8 hours
        return layoverMinutes >= 60 && layoverMinutes <= 480;
    }

    private long calculateLayoverMinutes(Flight first, Flight second) {
        LocalTime firstArrival = first.getArrivalTime();
        LocalTime secondDeparture = second.getDepartureTime();

        int arrivalMinutes = firstArrival.getHour() * 60 + firstArrival.getMinute();
        int departureMinutes = secondDeparture.getHour() * 60 + secondDeparture.getMinute();

        long layover = departureMinutes - arrivalMinutes;

        // Handle overnight connections
        if (layover < 0) {
            layover += 24 * 60;
        }

        return layover;
    }

    // ✅ NEW: Update available seats after booking
    public boolean updateAvailableSeats(Long flightId, Integer seatsBooked) {
        System.out.println("💺 Updating seats for flight ID: " + flightId);
        System.out.println("   Seats to book: " + seatsBooked);

        try {
            // Fetch the flight
            Flight flight = flightRepository.findById(flightId).orElse(null);

            if (flight == null) {
                System.out.println("❌ Flight not found");
                return false;
            }

            // Check if enough seats are available
            if (flight.getAvailableSeats() < seatsBooked) {
                System.out.println("❌ Not enough seats available");
                System.out.println("   Available: " + flight.getAvailableSeats());
                System.out.println("   Requested: " + seatsBooked);
                return false;
            }

            // Update available seats
            int newAvailableSeats = flight.getAvailableSeats() - seatsBooked;
            flight.setAvailableSeats(newAvailableSeats);

            // Save the updated flight
            flightRepository.save(flight);

            System.out.println("✅ Seats updated successfully");
            System.out.println("   Previous: " + (newAvailableSeats + seatsBooked));
            System.out.println("   Current: " + newAvailableSeats);

            return true;

        } catch (Exception e) {
            System.out.println("❌ Error updating seats: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, Flight updatedFlight) {
        if (flightRepository.existsById(id)) {
            updatedFlight.setId(id);
            return flightRepository.save(updatedFlight);
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
}