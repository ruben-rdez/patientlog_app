package org.patientlog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.patientlog.api.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
