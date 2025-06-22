package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.Doctor;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.entity.User;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser(User user);

    Optional<Doctor> findByRoom(Room room);

    Optional<Doctor> findBySpeciality(Speciality speciality);
}