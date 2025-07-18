package uz.dev.hmsproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.entity.Doctor;
import uz.dev.hmsproject.enums.AppointmentStatus;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.projection.DailyAppointment;
import uz.dev.hmsproject.projection.DoctorActivity;
import uz.dev.hmsproject.projection.StatisticsProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorAndAppointmentDateTime(Doctor doctor, LocalDateTime appointmentDateTime);

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetweenAndStatusNot(Long id,
                                                                              LocalDateTime appointmentDateTimeStart,
                                                                              LocalDateTime appointmentDateTimeEnd,
                                                                              AppointmentStatus status);

    Page<Appointment> findAllByAppointmentDateTimeBetween(LocalDateTime localDateTime,
                                                          LocalDateTime localDateTime1,
                                                          Pageable pageable);

    List<Appointment> findAllByAppointmentDateTimeBetween(LocalDateTime localDateTime,
                                                          LocalDateTime localDateTime1);

    Page<Appointment> findAllByPatientId(Long patientId, Pageable pageable);

    @Query("""
                 SELECT\s
                   COUNT(a)                                                    AS totalAppointments,
                   (SELECT COUNT(p) FROM Patient p WHERE p.deleted = false)   AS totalPatients,
                   SUM(CASE WHEN a.status = uz.dev.hmsproject.enums.AppointmentStatus.CANCELED THEN 1 ELSE 0 END)
                                                                              AS totalCanceledAppointments
                 FROM Appointment a
                 WHERE a.deleted = false
                   AND a.appointmentDateTime >= COALESCE(:startDate, a.appointmentDateTime)
                   AND a.appointmentDateTime <= COALESCE(:endDate,   a.appointmentDateTime)
            \s""")
    StatisticsProjection getStatistics(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    @Query("""
            SELECT FUNCTION('DATE', a.appointmentDateTime) AS date, COUNT(a) AS count\s
                        FROM Appointment a\s
                        WHERE a.deleted = false\s
                        AND a.appointmentDateTime BETWEEN :startDateTime AND :endDateTime\s
                        GROUP BY FUNCTION('DATE', a.appointmentDateTime)\s
                        ORDER BY FUNCTION('DATE', a.appointmentDateTime)""")
    List<DailyAppointment> getDailyAppointments(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("""
                 SELECT d.id AS doctorId,
                        u.fullName AS doctorFullName,
                        COUNT(a.id) AS appointmentCount
                 FROM Appointment a
                 JOIN a.doctor d
                 JOIN d.user u
                 WHERE a.deleted = false
                   AND (a.appointmentDateTime >= COALESCE(:startDate, a.appointmentDateTime))
                   AND (a.appointmentDateTime <= COALESCE(:endDate, a.appointmentDateTime))
                 GROUP BY d.id, u.fullName
                 ORDER BY u.fullName
            """)
    List<DoctorActivity> getDoctorActivities(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    List<Appointment> findAllByStatus(AppointmentStatus appointmentStatus);

    List<Appointment> findAllByAppointmentDateTimeBetweenAndStatus(LocalDateTime from,
                                                                   LocalDateTime to,
                                                                   AppointmentStatus appointmentStatus);

    default Appointment findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID : " + id, HttpStatus.NOT_FOUND));

    }

}