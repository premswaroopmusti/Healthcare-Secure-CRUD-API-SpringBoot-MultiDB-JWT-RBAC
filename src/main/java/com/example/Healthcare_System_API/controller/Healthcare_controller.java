package com.example.Healthcare_System_API.controller;

import com.example.Healthcare_System_API.model.h2.Records;
import com.example.Healthcare_System_API.service.Healthcare_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class Healthcare_controller {

    @Autowired
    private Healthcare_service service;


    // Patient: see only their own records
    @PreAuthorize("hasAuthority('view_my_records')")
    @GetMapping("/my")
    public ResponseEntity<?>getMyRecords(){

        return ResponseEntity.ok(service.getRecordsForCurrentPatient());

    }

    // Doctor/Nurse: view records by patient id
    @GetMapping("/{patientId}")
    @PreAuthorize("hasAuthority('view_records_by_patient_id')")
    public ResponseEntity<?>  getRecordsByPatientId(@PathVariable int patientId){

        return ResponseEntity.ok(service.getRecordsByPatientId(patientId));

    }

    // Doctor/Nurse: view all records
    @GetMapping
    @PreAuthorize("hasAuthority('view_all_records')")
    public ResponseEntity<List<Records>>  getAllRecords(){

        return ResponseEntity.ok(service.getAllRecords());
    }


    @PatchMapping("/{patientId}/status")
    @PreAuthorize("hasAuthority('update_status')")
    public ResponseEntity<?> updatePatientStatus(@PathVariable int patientId,
                                                 @RequestBody PatientStatus patientStatus) {

        Records updated = service.updatePatientStatus(patientId, patientStatus);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    //Doctor prescribes medication
    @PostMapping("/{patientId}/prescriptions")
    @PreAuthorize("hasAuthority('prescribe_medication')")
    public ResponseEntity<?> prescribeMedication(
            @PathVariable int patientId,
            @RequestBody PrescriptionRequest request){

        service.prescribeMedication(patientId, request);
        return ResponseEntity.status(201).build(); // 201 created
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create_record')")
    public ResponseEntity<?> createRecord(@RequestBody Records record){

        try{
                Records record1 = service.createRecord(record);
                return new ResponseEntity<>(record1, HttpStatus.CREATED);
        }
        catch(Exception e){

                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public record PrescriptionRequest(String Medication, String Dosage){

        public String getMedication() {
            return Medication;
        }

        public String getDosage(){
            return Dosage;
        }
    }

    public record PatientStatus(String status){

        public String getStatus(){
            return status;
        }

    }

}
