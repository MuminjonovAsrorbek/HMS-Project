package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
                SELECT a FROM Appointment a
                WHERE a.doctor.id = :doctorId
                AND DATE(a.dateTime) = :date
                AND a.status <> 'canceled'
            """)
    List<Appointment> findByDoctorIdAndDateTime(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}