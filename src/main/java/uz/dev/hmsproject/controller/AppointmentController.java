package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.request.AppointmentFilterRequest;
import uz.dev.hmsproject.dto.request.CreateAppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.service.template.AppointmentService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:27
 **/

@RestController("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('APPOINTMENTS_CREATE')")
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody @Valid CreateAppointmentDTO createAppointmentDTO) {

        appointmentService.createAppointment(createAppointmentDTO);

        return ResponseEntity.ok("Appointment created successfully");
    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {

        return appointmentService.getAppointmentById(id);

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/filter")
    public List<AppointmentDTO> filterAppointments(AppointmentFilterRequest appointmentFilterRequest) {

        return appointmentService.filterAppointments(appointmentFilterRequest);

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody @Valid CreateAppointmentDTO appointmentDTO) {

        appointmentService.updateAppointment(id, appointmentDTO);

        return ResponseEntity.ok("Appointment updated successfully");

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok("Appointment deleted successfully");

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_CHANGE_STATUS')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeAppointmentStatus(@PathVariable Long id, String status) {

        appointmentService.changeAppointmentStatus(id, status);

        return ResponseEntity.ok("Appointment status updated successfully");

    }
}
