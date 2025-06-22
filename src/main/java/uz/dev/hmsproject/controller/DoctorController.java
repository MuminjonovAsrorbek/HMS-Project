package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.DoctorDTO;
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

    @GetMapping
    public List<DoctorDTO> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable("id") Long id) {
        return doctorService.getById(id);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.create(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
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
