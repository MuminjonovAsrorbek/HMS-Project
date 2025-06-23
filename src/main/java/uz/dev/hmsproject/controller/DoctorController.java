package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.DoctorFilterDTO;
import uz.dev.hmsproject.dto.response.DoctorResponseDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.DoctorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PreAuthorize(value = "hasAuthority('FILTER_DOCTORS')")
    @GetMapping("/filter")
    public List<DoctorResponseDTO> filter(DoctorFilterDTO dto) {
        return doctorService.filter(dto);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_DOCTORS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return doctorService.getAllPaginated(page, size);
    }


    @PreAuthorize(value = "hasAuthority('VIEW_DOCTOR')")
    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable("id") Long id) {
        return doctorService.getById(id);
    }

    @PreAuthorize(value = "hasAuthority('CREATE_DOCTORS')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.create(doctorDTO);
        return ResponseEntity.ok("Doctor created successfully");
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTORS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        return ResponseEntity.ok("Doctor updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_DOCTORS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        doctorService.delete(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<List<String>> getAvailableSlots(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<LocalTime> slots = doctorService.getAvailable20MinuteSlots(doctorId, date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> formatted = slots.stream()
                .map(time -> time.format(formatter))
                .toList();

        return ResponseEntity.ok(formatted);
    }
}
