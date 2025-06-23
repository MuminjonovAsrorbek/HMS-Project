package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.dev.hmsproject.entity.Patient;

/**
 * Created by: suhrob
 */
public interface PatientRepository extends JpaRepository<Patient,Long> ,JpaSpecificationExecutor<Patient> {

    boolean existsByPhoneNumber(String phoneNumber);

}
