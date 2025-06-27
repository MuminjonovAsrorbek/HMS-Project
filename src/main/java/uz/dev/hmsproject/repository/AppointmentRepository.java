package uz.dev.hmsproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.entity.Doctor;
import uz.dev.hmsproject.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorAndAppointmentDateTime(Doctor doctor, LocalDateTime appointmentDateTime);

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetweenAndStatusNot(Long id, LocalDateTime appointmentDateTimeStart, LocalDateTime appointmentDateTimeEnd, AppointmentStatus status);

    Page<Appointment> findAllByAppointmentDateTimeBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);

}