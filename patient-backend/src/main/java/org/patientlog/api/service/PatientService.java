package org.patientlog.api.service;

import org.patientlog.api.dto.PatientResponse;
import org.patientlog.api.dto.PatientRequest;
import org.patientlog.api.exception.PatientNotFoundException;
import org.patientlog.api.model.Patient;
import org.patientlog.api.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.patientlog.api.util.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public PatientResponse getPatientById(Long id) {
        logger.info("Fetching patient with id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with id: {}", id);
                    return new PatientNotFoundException("Patient not found with id: " + id);
                });
        return patientMapper.toResponse(patient);
    }

    public PatientResponse savePatient(PatientRequest patientRequest) {
        logger.info("Saving new patient: {}", patientRequest);
        Patient patient = patientMapper.toEntity(patientRequest);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(savedPatient);
    }

    public void deletePatient(Long id) {
        logger.info("Deleting patient with id: {}", id);
        patientRepository.deleteById(id);
    }

    public List<PatientResponse> getAllPatients() {
        logger.info("Fetching all patients");
        return patientRepository.findAll().stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    public PatientResponse updatePatient(Long id, PatientRequest patientRequest) {
        logger.info("Updating patient with id: {}", id);
        if (patientRepository.existsById(id)) {
            Patient patient = patientMapper.toEntity(patientRequest);
            patient.setId(id);
            Patient updatedPatient = patientRepository.save(patient);
            return patientMapper.toResponse(updatedPatient);
        } else {
            logger.error("Patient not found with id: {}", id);
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
    }
}
