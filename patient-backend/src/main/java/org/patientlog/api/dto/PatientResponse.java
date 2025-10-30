package org.patientlog.api.dto;

public record PatientResponse(
    Long id, 
    String firstName, 
    String lastName, 
    Integer age) {
}
