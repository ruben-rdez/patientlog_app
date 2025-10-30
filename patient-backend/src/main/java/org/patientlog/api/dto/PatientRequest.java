package org.patientlog.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record PatientRequest(
    Long id,
    @NotNull(message = "firstName must not be null")
    String firstName,
    @NotNull(message = "lastName must not be null")
    String lastName,
    @NotNull(message = "age must not be null")
    @Min(value = 0, message = "age must be non-negative")
    @Max(value = 99, message = "age must be less than or equal to 99")
    Integer age) {

}
