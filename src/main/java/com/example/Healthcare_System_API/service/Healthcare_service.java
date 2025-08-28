package com.example.Healthcare_System_API.service;

import com.example.Healthcare_System_API.controller.Healthcare_controller;
import com.example.Healthcare_System_API.model.h2.Records;
import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.repository.postgres.Audit_logs_repo;
import com.example.Healthcare_System_API.repository.h2.Healthcare_repo;
import com.example.Healthcare_System_API.repository.postgres.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Healthcare_service {

        @Autowired
        private Audit_log_service auditLogService;

        @Autowired
        private Healthcare_repo healthcare_repo;

        @Autowired
        private UsersRepository usersRepository;

        public List<Records> getRecordsForCurrentPatient(){

            Integer currentUserId = auditLogService.ResolveCurrentUserId();
            // find user from DB
            Users user = usersRepository.findById(currentUserId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // use the linked patientId
            int patientId = user.getPatientId();

            return healthcare_repo.findAllByPatientId(patientId);

        }

        public List<Records> getRecordsByPatientId(int patientId){

            return healthcare_repo.findByPatientId(patientId);
        }

        public  List<Records>  getAllRecords(){

            return healthcare_repo.findAll();

        }

        public Records createRecord(Records record){

            Records saved = healthcare_repo.save(record);
            auditLogService.logCurrentUser("CREATED_RECORD", saved.getPatientId());
            return saved;
        }


        public void prescribeMedication(int patientId, Healthcare_controller.PrescriptionRequest request){

            // find record for this patient
                Records r1 = healthcare_repo.findFirstByPatientIdOrderByCreatedAtDesc(patientId)
                        .orElseThrow(() -> new IllegalArgumentException("Record not found for patientId " + patientId));

            // update data column with prescription info
            String prescriptionText = "Medication: " + request.getMedication() +
                    ", Dosage: " + request.getDosage();

            r1.setData(prescriptionText);

            healthcare_repo.save(r1);
            auditLogService.logCurrentUser("Prescribed_Medication", patientId);
        }

    public Records updatePatientStatus(int patientId, Healthcare_controller.PatientStatus patientStatus){

            Records record = healthcare_repo.findFirstByPatientIdOrderByCreatedAtDesc(patientId)
                            .orElseThrow(() -> new RuntimeException("Record not found for patientId " + patientId));

            String status = "Patient_Status: " + patientStatus.getStatus();

            record.setStatus(status);
            auditLogService.logCurrentUser("Updated_Patient_Status", patientId);
            return healthcare_repo.save(record);

    }

}
