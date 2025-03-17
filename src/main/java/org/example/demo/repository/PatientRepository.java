package org.example.demo.repository;

import org.example.demo.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PatientRepository extends JpaRepository<Patient, Long> {

}
