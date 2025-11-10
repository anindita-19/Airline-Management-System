// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/PassengerService.java
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Passenger;
import com.airline.nimbus.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    public Passenger createPassenger(Passenger passenger) {
        if (passengerRepository.existsByEmail(passenger.getEmail())) {
            return null; // Email already exists
        }
        return passengerRepository.save(passenger);
    }

    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        if (passenger != null) {
            passenger.setFirstName(passengerDetails.getFirstName());
            passenger.setLastName(passengerDetails.getLastName());
            passenger.setPhone(passengerDetails.getPhone());
            passenger.setAge(passengerDetails.getAge());
            passenger.setGender(passengerDetails.getGender());
            return passengerRepository.save(passenger);
        }
        return null;
    }

    public boolean deletePassenger(Long id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Passenger login(String email, String password) {
        return passengerRepository.findByEmailAndPassword(email, password);
    }

    public Passenger getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }
}
