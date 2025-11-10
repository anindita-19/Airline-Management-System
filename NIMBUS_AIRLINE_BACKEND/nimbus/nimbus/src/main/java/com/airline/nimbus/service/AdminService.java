// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/AdminService.java
// CREATE NEW FILE
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Admin;
import com.airline.nimbus.model.Flight;
import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.model.Booking;
import com.airline.nimbus.repository.AdminRepository;
import com.airline.nimbus.repository.FlightRepository;
import com.airline.nimbus.repository.BookingRepository;
import com.airline.nimbus.repository.PassengerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;


    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    // Admin login
    public Admin login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }

    // Create admin
    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            return null; // Email already exists
        }
        return adminRepository.save(admin);
    }

    // Update admin
    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
           // admin.setUsername(adminDetails.getUsername());
            admin.setPassword(adminDetails.getPassword());
            admin.setEmail(adminDetails.getEmail());
            return adminRepository.save(admin);
        }
        return null;
    }

    // Delete admin
    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}