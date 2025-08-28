package com.example.Healthcare_System_API.repository.postgres;

import com.example.Healthcare_System_API.model.postgres.Audit_Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Audit_logs_repo extends JpaRepository<Audit_Logs, Integer> {

}
