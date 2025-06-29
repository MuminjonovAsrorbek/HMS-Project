package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.request.AppointmentFilterRequest;
import uz.dev.hmsproject.dto.request.AppointmentRescheduleRequest;
import uz.dev.hmsproject.dto.request.CreateAppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:28
 **/

public interface AppointmentService {

    void createAppointment(CreateAppointmentDTO createAppointmentDTO);

    AppointmentDTO getAppointmentById(Long id);

    List<AppointmentDTO> filterAppointments(AppointmentFilterRequest appointmentFilterRequest);

    void updateAppointment(Long id, CreateAppointmentDTO appointmentDTO);

    void deleteAppointment(Long id);

    void changeAppointmentStatus(Long id, String status);

    PageableDTO getTodayAppointments(Integer page);

    List<Appointment> getAppointmentsByDate(LocalDate date);

    void changeStatus();

    void reschedule(AppointmentRescheduleRequest dto);
}
