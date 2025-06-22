package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.hmsproject.entity.Patient;

/**
 * Created by: suhrob
 */
public interface PatientRepository extends JpaRepository<Patient,Long> {

}
