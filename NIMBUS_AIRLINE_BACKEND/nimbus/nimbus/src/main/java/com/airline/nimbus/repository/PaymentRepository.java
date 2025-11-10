// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/PaymentRepository.java
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Payment;
import com.airline.nimbus.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByBooking(Booking booking);

    Payment findByTransactionId(String transactionId);
}