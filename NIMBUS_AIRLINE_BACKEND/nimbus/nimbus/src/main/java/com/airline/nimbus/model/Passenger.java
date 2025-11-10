// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/model/Passenger.java
// REPLACE the existing Passenger.java with this
// ============================================

package com.airline.nimbus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    private Integer age;

    private String gender;

    // NEW: Profile photo URL
    private String photoUrl;
}
