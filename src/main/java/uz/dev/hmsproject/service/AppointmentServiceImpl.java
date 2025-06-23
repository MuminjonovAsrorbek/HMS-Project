package uz.dev.hmsproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.request.AppointmentFilterRequest;
import uz.dev.hmsproject.dto.request.CreateAppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.entity.*;
import uz.dev.hmsproject.enums.AppointmentStatus;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.mapper.AppointmentMapper;
import uz.dev.hmsproject.repository.*;
import uz.dev.hmsproject.service.template.AppointmentService;
import uz.dev.hmsproject.utils.SecurityUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:30
 **/

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final SecurityUtils securityUtils;

    private final WorkSchedulerRepository schedulerRepository;

    private final PriceListRepository priceListRepository;

    private final AppointmentMapper appointmentMapper;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void createAppointment(CreateAppointmentDTO dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + dto.getDoctorId(), HttpStatus.NOT_FOUND));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + dto.getPatientId(), HttpStatus.NOT_FOUND));

        User currentUser = securityUtils.getCurrentUser();

        LocalDate date = dto.getAppointmentDateTime().toLocalDate();
        LocalTime time = dto.getAppointmentDateTime().toLocalTime();

        WorkScheduler schedule = schedulerRepository.findByUserIdAndDayOfWeek(
                        doctor.getUser().getId(), date.getDayOfWeek().getValue())
                .orElseThrow(() -> new RuntimeException("Doctor doesn't work that day"));

        if (time.isBefore(schedule.getStartTime()) ||
                time.plusMinutes(20).isAfter(schedule.getEndTime())) {
            throw new RuntimeException("Time is outside doctor's schedule");
        }

        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateTime(doctor, dto.getAppointmentDateTime());

        if (exists) throw new RuntimeException("This time slot is already booked");

        BigDecimal price = priceListRepository.findBySpecialityId(doctor.getSpeciality().getId())
                .orElseThrow(() -> new RuntimeException("Price not found")).getPrice();

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setPrice(price);
        appointment.setRoomId(doctor.getRoom().getId());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCreatedBy(currentUser);

        appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND));

        return appointmentMapper.toDTO(appointment);

    }

    @Override
    public List<AppointmentDTO> filterAppointments(AppointmentFilterRequest request) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        Root<Appointment> root = cq.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.getDoctorId() != null) {
            predicates.add(cb.equal(root.get("doctor").get("id"), request.getDoctorId()));
        }
        if (request.getPatientId() != null) {
            predicates.add(cb.equal(root.get("patient").get("id"), request.getPatientId()));
        }
        if (request.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), request.getStatus()));
        }
        if (request.getDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("appointmentDatetime"), request.getDateFrom().atStartOfDay()));
        }
        if (request.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("appointmentDatetime"), request.getDateTo().atTime(LocalTime.MAX)));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        cq.orderBy(cb.asc(root.get("appointmentDatetime")));

        List<Appointment> results = entityManager.createQuery(cq).getResultList();

        return appointmentMapper.toDTO(results);

    }

    @Override
    @Transactional
    public void updateAppointment(Long id, CreateAppointmentDTO dto) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + dto.getDoctorId(), HttpStatus.NOT_FOUND));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + dto.getPatientId(), HttpStatus.NOT_FOUND));

        LocalDate date = dto.getAppointmentDateTime().toLocalDate();
        LocalTime time = dto.getAppointmentDateTime().toLocalTime();

        WorkScheduler schedule = schedulerRepository.findByUserIdAndDayOfWeek(
                        doctor.getUser().getId(), date.getDayOfWeek().getValue())
                .orElseThrow(() -> new RuntimeException("Doctor doesn't work that day"));

        if (time.isBefore(schedule.getStartTime()) ||
                time.plusMinutes(20).isAfter(schedule.getEndTime())) {
            throw new RuntimeException("Time is outside doctor's schedule");
        }

        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateTime(doctor, dto.getAppointmentDateTime());

        if (exists) throw new RuntimeException("This time slot is already booked");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());

        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND));

        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new RuntimeException("Appointment is already canceled");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

    }

    @Override
    @Transactional
    public void changeAppointmentStatus(Long id, String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND));

        try {
            AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
            appointment.setStatus(appointmentStatus);
            appointmentRepository.save(appointment);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status, e);
        }

    }

}
