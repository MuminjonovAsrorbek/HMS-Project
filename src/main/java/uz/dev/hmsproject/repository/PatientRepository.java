package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import uz.dev.hmsproject.entity.Patient;
import uz.dev.hmsproject.exception.EntityNotFoundException;

/**
 * Created by: suhrob
 */
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    boolean existsByPhoneNumber(String phoneNumber);

    default Patient findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id, HttpStatus.BAD_REQUEST));

    }

}
