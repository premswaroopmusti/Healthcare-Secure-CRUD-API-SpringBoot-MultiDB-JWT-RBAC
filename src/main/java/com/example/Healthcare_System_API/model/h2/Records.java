package com.example.Healthcare_System_API.model.h2;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int patientId;

    @Column(columnDefinition = "TEXT")
    private String data = null; //  will store prescription text like "Medication: Paracetamol, Dosage: 500mg"

    private String status = "Admitted";  // will store patient status

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

}
