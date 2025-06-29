package uz.dev.hmsproject.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.enums.AppointmentStatus;
import uz.dev.hmsproject.mapper.AppointmentMapper;
import uz.dev.hmsproject.repository.AppointmentRepository;
import uz.dev.hmsproject.service.template.AppointmentService;
import uz.dev.hmsproject.service.template.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledService {

    private final AppointmentService appointmentService;

    private final AppointmentRepository appointmentRepository;

    private final NotificationService NotificationService;

    private AppointmentMapper appointmentMapper;

    @Scheduled(cron = "0 0 21 * * *")
//    @Scheduled(cron = "0 36 18 * * *")
    @Transactional
    public void changeStatus() {
        appointmentService.changeStatus();
    }

    // Admission time admissions 2 hours before approaching the attention is to send an email notice
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void sendUpcomingAppointmentReminders() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = now.plusHours(2).minusMinutes(5);
        LocalDateTime to = now.plusHours(2).plusMinutes(5);

        List<Appointment> upcomingAppointments = appointmentRepository
                .findAllByAppointmentDateTimeBetweenAndStatus(from, to, AppointmentStatus.SCHEDULED);

        for (Appointment appointment : upcomingAppointments) {
            String patientEmail = appointment.getPatient().getEmail();
            if (patientEmail != null && !patientEmail.isBlank()) {

                String subject = "📅 Qabul eslatmasi";

                NotificationService.sendEmail(patientEmail, subject, appointmentMapper.toDTO(appointment));
            }
        }

    }
}
