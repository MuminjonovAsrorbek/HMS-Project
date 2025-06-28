package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentRespDTO;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.mapper.template.BaseMapper;
import uz.dev.hmsproject.repository.RoomRepository;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:53
 **/

@Component
@RequiredArgsConstructor
public class AppointmentMapper implements BaseMapper<Appointment, AppointmentDTO> {

    private final RoomRepository roomRepository;

    @Override
    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        return null;
    }

    @Override
    public AppointmentDTO toDTO(Appointment appointment) {

        Room room = roomRepository.findById(appointment.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + appointment.getRoomId()));

        return new AppointmentDTO(
                appointment.getId(),
                appointment.getPatient().getFullName(),
                appointment.getDoctor().getUser().getFullName(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus(),
                room.getNumber(),
                appointment.getPrice()
        );

    }

    @Override
    public List<AppointmentDTO> toDTO(List<Appointment> appointments) {

        return appointments.stream().map(this::toDTO).toList();

    }

    public AppointmentRespDTO toRespDTO(Appointment appointment) {

        Room room = roomRepository.findById(appointment.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + appointment.getRoomId()));

        return new AppointmentRespDTO(
                appointment.getId(),
                appointment.getDoctor().getUser().getFullName(),
                appointment.getDoctor().getSpeciality().getName(),
                appointment.getPatient().getFullName(),
                appointment.getPatient().getEmail(),
                appointment.getPatient().getPhoneNumber(),
                appointment.getAppointmentDateTime(),
                room.getNumber(),
                appointment.getPrice(),
                appointment.getStatus()
        );

    }

    public List<AppointmentRespDTO> toRespDTO(List<Appointment> appointments) {

        return appointments.stream().map(this::toRespDTO).toList();

    }
}
