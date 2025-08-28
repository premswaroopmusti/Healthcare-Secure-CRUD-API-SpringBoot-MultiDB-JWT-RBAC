package com.example.Healthcare_System_API.repository.h2;

import com.example.Healthcare_System_API.model.h2.Records;
import com.example.Healthcare_System_API.model.postgres.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Healthcare_repo extends JpaRepository<Records, Integer> {

        List<Records> findByPatientId(int patientId);

        List<Records> findAllByPatientId(int patientId);

        // fetch latest one record for updates
        Optional<Records> findFirstByPatientIdOrderByCreatedAtDesc(int patientId);
}
