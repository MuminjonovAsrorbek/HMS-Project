package uz.dev.hmsproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.request.AppointmentFilterRequest;
import uz.dev.hmsproject.dto.request.AppointmentRescheduleRequest;
import uz.dev.hmsproject.dto.request.CreateAppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentRespDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.AppointmentStatus;
import uz.dev.hmsproject.exception.*;
import uz.dev.hmsproject.mapper.AppointmentMapper;
import uz.dev.hmsproject.repository.*;
import uz.dev.hmsproject.service.template.AppointmentService;
import uz.dev.hmsproject.service.template.NotificationService;
import uz.dev.hmsproject.utils.CommonUtils;
import uz.dev.hmsproject.utils.SecurityUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private final NotificationService notificationService;

    @Override
    @Transactional
    public void createAppointment(CreateAppointmentDTO dto) {

        LocalDate today = LocalDate.now();

        if (dto.getAppointmentDateTime().toLocalDate().isBefore(today)) {

            throw new AppointmentDateExpiredException("Appointments can only be scheduled for today or future dates.", HttpStatus.BAD_REQUEST);

        }

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + dto.getDoctorId(), HttpStatus.NOT_FOUND));

        if (!doctor.getUser().isActive())
            throw new EntityNotFoundException("The doctor isn't active now", HttpStatus.BAD_REQUEST);

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + dto.getPatientId(), HttpStatus.NOT_FOUND));

        User currentUser = securityUtils.getCurrentUser();

        LocalDate date = dto.getAppointmentDateTime().toLocalDate();
        LocalTime time = dto.getAppointmentDateTime().toLocalTime();

        WorkScheduler schedule = schedulerRepository.findByUserIdAndDayOfWeek(
                        doctor.getUser().getId(), date.getDayOfWeek().getValue())
                .orElseThrow(() -> new EntityNotWorkException("Doctor doesn't work that day", HttpStatus.BAD_REQUEST));

        if (time.isBefore(schedule.getStartTime()) ||
                time.plusMinutes(20).isAfter(schedule.getEndTime())) {

            throw new EntityNotWorkException("Time is outside doctor's schedule", HttpStatus.BAD_REQUEST);

        }

        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateTime(doctor, dto.getAppointmentDateTime());

        if (exists) throw new EntityNotWorkException("This time slot is already booked", HttpStatus.BAD_REQUEST);

        BigDecimal price = priceListRepository.findBySpecialityId(doctor.getSpeciality().getId())
                .orElseThrow(() -> new EntityNotFoundException("Price not found", HttpStatus.NOT_FOUND)).getPrice();

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setPrice(price);
        appointment.setRoomId(doctor.getRoom().getId());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCreatedBy(currentUser);

        appointmentRepository.save(appointment);

        if (Objects.nonNull(patient.getEmail()) && !patient.getEmail().isBlank()) {

            String subject = "Yangi qabul ro'yxatdan o'tkazildi";
            String patientEmail = appointment.getPatient().getEmail();

            notificationService.sendEmail(patientEmail, subject, appointmentMapper.toDTO(appointment));

        }
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
            predicates.add(cb.greaterThanOrEqualTo(root.get("appointmentDateTime"), request.getDateFrom().atStartOfDay()));
        }
        if (request.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("appointmentDateTime"), request.getDateTo().atTime(LocalTime.MAX)));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        cq.orderBy(cb.asc(root.get("appointmentDateTime")));

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

        if (!doctor.getUser().isActive())
            throw new EntityNotFoundException("The doctor isn't active now", HttpStatus.BAD_REQUEST);

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
            throw new EntityAlreadyExistsException("Appointment is already canceled", HttpStatus.CONFLICT);
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

    }

    @Override
    @Transactional
    public void changeAppointmentStatus(Long id, String status) {

        if (status == null || status.isBlank()) {
            throw new EntityNotNullException("Status cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND));

        try {
            AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());

            if (appointmentStatus == AppointmentStatus.CANCELED) {

                if (appointment.getAppointmentDateTime().minusHours(1).isBefore(LocalDateTime.now())) {

                    throw new AppointmentDateExpiredException("Appointment cannot be canceled less than 1 hour before it starts.", HttpStatus.BAD_REQUEST);
                }

            }

            appointment.setStatus(appointmentStatus);
            appointmentRepository.save(appointment);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status, e);
        }


    }

    @Override
    public PageableDTO getTodayAppointments(Integer page) {

        LocalDate today = LocalDate.now();

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, 10, sort);

        LocalDateTime startDay = today.atStartOfDay();

        LocalDateTime endDay = today.plusDays(1).atStartOfDay();

        Page<Appointment> appointmentPage = appointmentRepository.findAllByAppointmentDateTimeBetween(
                startDay,
                endDay,
                pageable);

        List<Appointment> appointments = appointmentPage.getContent();

        List<AppointmentRespDTO> appointmentRespDTOS = appointmentMapper.toRespDTO(appointments);

        return new PageableDTO(
                appointmentPage.getSize(),
                appointmentPage.getTotalElements(),
                appointmentPage.getTotalPages(),
                !appointmentPage.isLast(),
                !appointmentPage.isFirst(),
                appointmentRespDTOS
        );
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {

        return appointmentRepository.findAllByAppointmentDateTimeBetween(date.atStartOfDay(),
                date.plusDays(1).atStartOfDay());

    }

    @Override
    @Transactional
    public void changeStatus() {

        List<Appointment> appointments = appointmentRepository.findAllByStatus(AppointmentStatus.SCHEDULED);

        if (appointments.isEmpty()) {
            return;
        }

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {

                appointment.setStatus(AppointmentStatus.EXPIRED);

                appointmentRepository.save(appointment);
            }
        }

    }

    @Override
    @Transactional
    public void reschedule(AppointmentRescheduleRequest dto) {

        Appointment appointment = appointmentRepository.findByIdOrThrow(dto.getAppointmentId());

        if (!appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {

            throw new EntityNotNullException("Can change Appointment only in Scheduled mode", HttpStatus.BAD_REQUEST);

        }

        LocalDate today = LocalDate.now();

        if (dto.getNewDateTime().toLocalDate().isBefore(today)) {

            throw new AppointmentDateExpiredException("Appointments can only be scheduled for today or future dates.", HttpStatus.BAD_REQUEST);

        }

        Doctor doctor = appointment.getDoctor();

        if (Objects.nonNull(dto.getNewDoctorId())) {

            if (!doctor.getUser().isActive())
                throw new EntityNotFoundException("The doctor isn't active now", HttpStatus.BAD_REQUEST);

            doctor = doctorRepository.findById(dto.getNewDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + dto.getNewDoctorId(), HttpStatus.NOT_FOUND));

        }

        LocalDate date = dto.getNewDateTime().toLocalDate();
        LocalTime time = dto.getNewDateTime().toLocalTime();

        WorkScheduler schedule = schedulerRepository.findByUserIdAndDayOfWeek(
                        doctor.getUser().getId(), date.getDayOfWeek().getValue())
                .orElseThrow(() -> new EntityNotWorkException("Doctor doesn't work that day", HttpStatus.BAD_REQUEST));

        if (time.isBefore(schedule.getStartTime()) ||
                time.plusMinutes(20).isAfter(schedule.getEndTime())) {
            throw new EntityNotWorkException("Time is outside doctor's schedule", HttpStatus.BAD_REQUEST);
        }

        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateTime(doctor, dto.getNewDateTime());

        if (exists) throw new EntityAlreadyExistsException("This time slot is already booked", HttpStatus.CONFLICT);

        appointment.setAppointmentDateTime(dto.getNewDateTime());
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);

        sendEmail(appointment);
    }

    @Transactional
    public void sendEmail(Appointment appointment) {

        String email = appointment.getPatient().getEmail();
        if (email != null && !email.isBlank()) {

            String subject = "Qabul vaqti o'zgartirildi";
            String message = String.format("""
                            <html>
                              <head>
                                <style>
                                  body { font-family: Arial, sans-serif; background: #f9f9f9; color: #222; }
                                  .container { background: #fff; border-radius: 8px; padding: 24px; max-width: 480px; margin: 0 auto; box-shadow: 0 2px 8px #eee; }
                                  .header { font-size: 20px; font-weight: bold; margin-bottom: 16px; color: #2a7ae2; }
                                  .info { margin: 12px 0; }
                                  .label { font-weight: bold; color: #555; }
                                  .footer { margin-top: 24px; color: #888; font-size: 14px; }
                                </style>
                              </head>
                              <body>
                                <div class="container">
                                  <div class="header">Hurmatli %s</div>
                                  <div class="info">Sizning qabulingiz yangi vaqtga ko&#39;chirildi:</div>
                                  <div class="info"><span class="label">Sana-vaqt:</span> %s</div>
                                  <div class="info"><span class="label">Shifokor:</span> %s</div>
                                  <div class="info"><span class="label">Xona:</span> %s</div>
                                  <div class="info"><span class="label">Narx:</span> %s so&#39;m</div>
                                  <div class="footer">E&#39;tiboringiz uchun rahmat.</div>
                                </div>
                              </body>
                            </html>
                            """,
                    appointment.getPatient().getFullName(),
                    CommonUtils.formattedDate(appointment.getAppointmentDateTime()),
                    appointment.getDoctor().getUser().getFullName(),
                    appointment.getDoctor().getRoom().getNumber(),
                    appointment.getPrice());
            notificationService.sendEmail(email, subject, message);
        }

    }
}