// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/repository/AdminRepository.java
// CREATE NEW FILE
// ============================================

package com.airline.nimbus.repository;

import com.airline.nimbus.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by email
    Admin findByEmail(String username);

    // Find admin by email and password
    Admin findByEmailAndPassword(String username, String password);

    // Check if email exists
    boolean existsByEmail(String username);
}