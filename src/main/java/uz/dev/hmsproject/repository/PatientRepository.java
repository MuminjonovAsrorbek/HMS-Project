package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.hmsproject.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}