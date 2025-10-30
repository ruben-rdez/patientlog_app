package org.patientlog.api.util;

import org.patientlog.api.model.Patient;
import org.patientlog.api.dto.PatientRequest;
import org.patientlog.api.dto.PatientResponse;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
            patient.getId(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getAge()
        );
    }

    public Patient toEntity(PatientRequest request) {
        return new Patient(
            request.id(),
            request.firstName(),
            request.lastName(),
            request.age()
        );
    }

}
