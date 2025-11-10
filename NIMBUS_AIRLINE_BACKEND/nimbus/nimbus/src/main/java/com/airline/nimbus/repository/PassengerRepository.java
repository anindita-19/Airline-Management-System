// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/PassengerRepository.java
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Passenger findByEmail(String email);

    Passenger findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
