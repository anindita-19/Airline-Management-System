// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/BookingService.java
// UPDATED: Multi-Seat Booking and Release Logic
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Booking;
import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.model.Flight;
import com.airline.nimbus.repository.BookingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatService seatService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(Booking booking) {
        System.out.println("=== BOOKING SERVICE: Creating Booking ===");
        System.out.println("Flight Class: " + booking.getFlightClass());
        System.out.println("Luggage: " + booking.getLuggageWeight() + "kg");
        System.out.println("Seat Number (legacy): " + booking.getSeatNumber());
        System.out.println("Seat Numbers (multi): " + booking.getSeatNumbers());

        // Check seats
        if (!flightService.updateAvailableSeats(
                booking.getFlight().getId(),
                booking.getNumberOfSeats())) {
            System.out.println("❌ Not enough seats");
            return null;
        }

        // Set date and status
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        // Ensure class is set
        if (booking.getFlightClass() == null || booking.getFlightClass().isEmpty()) {
            booking.setFlightClass("ECONOMY");
        }

        // Calculate Flight 1 price
        Double baseFare = calculateClassPrice(booking.getFlight(), booking.getFlightClass());
        System.out.println("Flight 1 fare (" + booking.getFlightClass() + "): ₹" + baseFare);

        // Add Flight 2 price if connecting
        if (Boolean.TRUE.equals(booking.getIsConnecting()) && booking.getConnectingFlightId() != null) {
            Flight flight2 = flightService.getFlightById(booking.getConnectingFlightId());
            if (flight2 != null) {
                Double fare2 = calculateClassPrice(flight2, booking.getFlightClass());
                baseFare += fare2;
                System.out.println("Flight 2 fare (" + booking.getFlightClass() + "): ₹" + fare2);
            }
        }

        System.out.println("Total base fare: ₹" + baseFare);

        // Calculate luggage charges
        Double luggageCharges = calculateLuggageCharges(
                booking.getFlightClass(),
                booking.getLuggageWeight()
        );
        System.out.println("Luggage charges: ₹" + luggageCharges);

        booking.setLuggageCharges(luggageCharges);

        // Total
        Double total = (baseFare * booking.getNumberOfSeats()) + luggageCharges;
        booking.setTotalAmount(total);
        System.out.println("TOTAL AMOUNT: ₹" + total);

        // ✅ NEW: Book multiple seats
        try {
            if (booking.getSeatNumbers() != null && !booking.getSeatNumbers().isEmpty()) {
                // Multi-seat booking (connecting flights)
                Map<String, String> seatMap = objectMapper.readValue(
                        booking.getSeatNumbers(),
                        new TypeReference<Map<String, String>>(){}
                );

                System.out.println("📍 Booking multiple seats: " + seatMap);

                // Book seat for flight 1
                if (seatMap.containsKey("flight1")) {
                    String seat1 = seatMap.get("flight1");
                    boolean booked1 = seatService.bookSeat(booking.getFlight().getId(), seat1);
                    System.out.println("Flight 1 seat " + seat1 + ": " + (booked1 ? "✅ Booked" : "❌ Failed"));
                }

                // Book seat for flight 2
                if (seatMap.containsKey("flight2") && booking.getConnectingFlightId() != null) {
                    String seat2 = seatMap.get("flight2");
                    boolean booked2 = seatService.bookSeat(booking.getConnectingFlightId(), seat2);
                    System.out.println("Flight 2 seat " + seat2 + ": " + (booked2 ? "✅ Booked" : "❌ Failed"));
                }

            } else if (booking.getSeatNumber() != null && !booking.getSeatNumber().isEmpty()) {
                // Single seat booking (direct flights - legacy)
                boolean booked = seatService.bookSeat(booking.getFlight().getId(), booking.getSeatNumber());
                System.out.println("Single seat " + booking.getSeatNumber() + ": " + (booked ? "✅ Booked" : "❌ Failed"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error booking seats: " + e.getMessage());
            e.printStackTrace();
        }

        return bookingRepository.save(booking);
    }

    private Double calculateClassPrice(Flight flight, String flightClass) {
        switch (flightClass.toUpperCase()) {
            case "BUSINESS":
                return flight.getBusinessPrice() != null ?
                        flight.getBusinessPrice() : flight.getPrice() * 2;
            case "FIRST":
                return flight.getFirstClassPrice() != null ?
                        flight.getFirstClassPrice() : flight.getPrice() * 3;
            default:
                return flight.getEconomyPrice() != null ?
                        flight.getEconomyPrice() : flight.getPrice();
        }
    }

    private Double calculateLuggageCharges(String flightClass, Double weight) {
        if (weight == null || weight == 0) return 0.0;

        double freeLimit = flightClass.equals("FIRST") ? 40.0 :
                flightClass.equals("BUSINESS") ? 30.0 : 20.0;

        return weight > freeLimit ? (weight - freeLimit) * 10.0 : 0.0;
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null && "CONFIRMED".equals(booking.getStatus())) {
            booking.setStatus("CANCELLED");
            Flight flight = booking.getFlight();
            flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());

            // ✅ NEW: Release multiple seats
            try {
                if (booking.getSeatNumbers() != null && !booking.getSeatNumbers().isEmpty()) {
                    // Multi-seat release (connecting flights)
                    Map<String, String> seatMap = objectMapper.readValue(
                            booking.getSeatNumbers(),
                            new TypeReference<Map<String, String>>(){}
                    );

                    System.out.println("📍 Releasing multiple seats: " + seatMap);

                    // Release seat for flight 1
                    if (seatMap.containsKey("flight1")) {
                        String seat1 = seatMap.get("flight1");
                        boolean released1 = seatService.releaseSeat(booking.getFlight().getId(), seat1);
                        System.out.println("Flight 1 seat " + seat1 + ": " + (released1 ? "✅ Released" : "❌ Failed"));
                    }

                    // Release seat for flight 2
                    if (seatMap.containsKey("flight2") && booking.getConnectingFlightId() != null) {
                        String seat2 = seatMap.get("flight2");
                        boolean released2 = seatService.releaseSeat(booking.getConnectingFlightId(), seat2);
                        System.out.println("Flight 2 seat " + seat2 + ": " + (released2 ? "✅ Released" : "❌ Failed"));
                    }

                } else if (booking.getSeatNumber() != null && !booking.getSeatNumber().isEmpty()) {
                    // Single seat release (direct flights - legacy)
                    boolean released = seatService.releaseSeat(flight.getId(), booking.getSeatNumber());
                    System.out.println("Single seat " + booking.getSeatNumber() + ": " + (released ? "✅ Released" : "❌ Failed"));
                }
            } catch (Exception e) {
                System.out.println("❌ Error releasing seats: " + e.getMessage());
                e.printStackTrace();
            }

            return bookingRepository.save(booking);
        }
        return null;
    }

    public List<Booking> getBookingsByPassenger(Passenger passenger) {
        return bookingRepository.findByPassenger(passenger);
    }

    public List<Booking> getBookingsByFlight(Flight flight) {
        return bookingRepository.findByFlight(flight);
    }
}