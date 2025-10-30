package org.patientlog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.patientlog.api.dto.PatientResponse;
import org.patientlog.api.dto.PatientRequest;
import org.patientlog.api.service.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
	@PreAuthorize("hasRole('CONSULTOR') or hasRole('ADMIN')")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        PatientResponse patientResponse = patientService.getPatientById(id);
        if (patientResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(patientResponse);
    }

    @PostMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientResponse> createPatient(
        @Valid @RequestBody PatientRequest patientRequest) {
            PatientResponse createdPatient = patientService.savePatient(patientRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientResponse> updatePatient(
        @PathVariable Long id, 
        @Valid @RequestBody PatientRequest patientRequest) {
        PatientResponse updatedPatient = patientService.updatePatient(id, patientRequest);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
	@PreAuthorize("hasRole('CONSULTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}
