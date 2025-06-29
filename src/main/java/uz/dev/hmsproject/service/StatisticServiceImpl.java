package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.response.StatisticsResponseDTO;
import uz.dev.hmsproject.projection.DailyAppointment;
import uz.dev.hmsproject.projection.DoctorActivity;
import uz.dev.hmsproject.projection.StatisticsProjection;
import uz.dev.hmsproject.repository.AppointmentRepository;
import uz.dev.hmsproject.service.template.StatisticService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 17:01
 **/

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final AppointmentRepository appointmentRepository;


    @Override
    public StatisticsResponseDTO getDailyStatistics(LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);

        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        StatisticsProjection statistics = appointmentRepository.getStatistics(startDateTime, endDateTime);

        List<DailyAppointment> dailyAppointments = appointmentRepository.getDailyAppointments(startDateTime, endDateTime);

        List<DoctorActivity> doctorActivities = appointmentRepository.getDoctorActivities(startDateTime, endDateTime);

        return new StatisticsResponseDTO(
                statistics.getTotalAppointments(),
                statistics.getTotalPatients(),
                statistics.getTotalCanceledAppointments(),
                dailyAppointments,
                doctorActivities
        );
    }
}
