package com.example.Healthcare_System_API.model.postgres;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Audit_Logs")
public class Audit_Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int user_id;
    @Column(nullable = false)
    private String action; //  CREATE_USER, CREATE_RECORD, UPDATE_PATIENT_STATUS, PRESCRIBE MEDICATION

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @Column(name = "patient_id")
    private int patientId;

    public Audit_Logs(String action, int patientId, int user_id){
        this.action = action;
        this.patientId = patientId;
    }

    public Audit_Logs(String action, int user_id){
        this.action = action;
        this.user_id = user_id;
    }

}
