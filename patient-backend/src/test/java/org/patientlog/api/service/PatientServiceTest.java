package org.patientlog.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.patientlog.api.dto.PatientRequest;
import org.patientlog.api.dto.PatientResponse;
import org.patientlog.api.exception.PatientNotFoundException;
import org.patientlog.api.model.Patient;
import org.patientlog.api.repository.PatientRepository;
import org.patientlog.api.util.PatientMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;
    private PatientRequest testPatientRequest;
    private PatientResponse testPatientResponse;

    @BeforeEach
    void setUp() {
        // Setup test data
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setAge(30);

        testPatientRequest = new PatientRequest(null, "John", "Doe", 30);
        testPatientResponse = new PatientResponse(1L, "John", "Doe", 30);
    }

    @Nested
    @DisplayName("Save Patient Tests")
    class SavePatientTests {
        
        @Test
        @DisplayName("Should successfully save a new patient")
        void shouldSavePatientSuccessfully() {
            // Given
            when(patientMapper.toEntity(any(PatientRequest.class))).thenReturn(testPatient);
            when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
            when(patientMapper.toResponse(any(Patient.class))).thenReturn(testPatientResponse);

            // When
            PatientResponse savedPatient = patientService.savePatient(testPatientRequest);

            // Then
            assertNotNull(savedPatient);
            assertEquals(testPatientResponse.id(), savedPatient.id());
            assertEquals(testPatientResponse.firstName(), savedPatient.firstName());
            verify(patientRepository).save(any(Patient.class));
            verify(patientMapper).toEntity(any(PatientRequest.class));
            verify(patientMapper).toResponse(any(Patient.class));
        }
    }

    @Nested
    @DisplayName("Get All Patients Tests")
    class GetAllPatientsTests {

        @Test
        @DisplayName("Should return list of all patients")
        void shouldReturnAllPatients() {
            // Given
            List<Patient> patients = List.of(testPatient);
            when(patientRepository.findAll()).thenReturn(patients);
            when(patientMapper.toResponse(any(Patient.class))).thenReturn(testPatientResponse);

            // When
            List<PatientResponse> result = patientService.getAllPatients();

            // Then
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            verify(patientRepository).findAll();
            verify(patientMapper).toResponse(any(Patient.class));
        }

        @Test
        @DisplayName("Should return empty list when no patients exist")
        void shouldReturnEmptyListWhenNoPatients() {
            // Given
            when(patientRepository.findAll()).thenReturn(List.of());

            // When
            List<PatientResponse> result = patientService.getAllPatients();

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(patientRepository).findAll();
            verify(patientMapper, never()).toResponse(any(Patient.class));
        }
    }

    @Nested
    @DisplayName("Get Patient By ID Tests")
    class GetPatientByIdTests {

        @Test
        @DisplayName("Should return patient when ID exists")
        void shouldReturnPatientWhenIdExists() {
            // Given
            when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
            when(patientMapper.toResponse(any(Patient.class))).thenReturn(testPatientResponse);

            // When
            PatientResponse result = patientService.getPatientById(1L);

            // Then
            assertNotNull(result);
            assertEquals(testPatientResponse.id(), result.id());
            assertEquals(testPatientResponse.firstName(), result.firstName());
            verify(patientRepository).findById(1L);
            verify(patientMapper).toResponse(testPatient);
        }

        @Test
        @DisplayName("Should throw PatientNotFoundException when ID doesn't exist")
        void shouldThrowExceptionWhenIdDoesNotExist() {
            // Given
            when(patientRepository.findById(99L)).thenReturn(Optional.empty());

            // When & Then
            assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(99L));
            verify(patientRepository).findById(99L);
            verify(patientMapper, never()).toResponse(any(Patient.class));
        }
    }

    @Nested
    @DisplayName("Update Patient Tests")
    class UpdatePatientTests {

        @Test
        @DisplayName("Should update patient successfully when ID exists")
        void shouldUpdatePatientWhenIdExists() {
            // Given
            when(patientRepository.existsById(1L)).thenReturn(true);
            when(patientMapper.toEntity(any(PatientRequest.class))).thenReturn(testPatient);
            when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
            when(patientMapper.toResponse(any(Patient.class))).thenReturn(testPatientResponse);

            // When
            PatientResponse updatedPatient = patientService.updatePatient(1L, testPatientRequest);

            // Then
            assertNotNull(updatedPatient);
            assertEquals(testPatientResponse.id(), updatedPatient.id());
            assertEquals(testPatientResponse.firstName(), updatedPatient.firstName());
            verify(patientRepository).existsById(1L);
            verify(patientRepository).save(any(Patient.class));
            verify(patientMapper).toEntity(testPatientRequest);
            verify(patientMapper).toResponse(testPatient);
        }

        @Test
        @DisplayName("Should throw PatientNotFoundException when updating non-existent patient")
        void shouldThrowExceptionWhenUpdatingNonExistentPatient() {
            // Given
            when(patientRepository.existsById(99L)).thenReturn(false);

            // When & Then
            assertThrows(PatientNotFoundException.class, 
                () -> patientService.updatePatient(99L, testPatientRequest));
            verify(patientRepository).existsById(99L);
            verify(patientRepository, never()).save(any(Patient.class));
            verify(patientMapper, never()).toEntity(any(PatientRequest.class));
            verify(patientMapper, never()).toResponse(any(Patient.class));
        }
    }

    @Nested
    @DisplayName("Delete Patient Tests")
    class DeletePatientTests {

        @Test
        @DisplayName("Should delete patient successfully")
        void shouldDeletePatientSuccessfully() {
            // When
            patientService.deletePatient(1L);

            // Then
            verify(patientRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Should not throw exception when deleting non-existent patient")
        void shouldNotThrowExceptionWhenDeletingNonExistentPatient() {
            // Given
            doNothing().when(patientRepository).deleteById(any());

            // When
            patientService.deletePatient(99L);

            // Then
            verify(patientRepository).deleteById(99L);
        }
    }
}