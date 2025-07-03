package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Doctor API", description = "A Doctor of full control of appointments")
public class DoctorController {

    private final DoctorService doctorService;

    @PreAuthorize(value = "hasAuthority('VIEW_DOCTORS')")
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
    public DoctorResponseDTO getById(@PathVariable("id") Long id) {
        return doctorService.getById(id);
    }

    @PreAuthorize(value = "hasAuthority('CREATE_DOCTOR')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.create(doctorDTO);
        return ResponseEntity.ok("Doctor created successfully");
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        return ResponseEntity.ok("Doctor updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        doctorService.delete(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @GetMapping("/{doctorId}/available-slots")
    @PreAuthorize(value = "hasAuthority('VIEW_DOCTOR')")
    @Operation(
            summary = "Get Doctor daily slots",
            description = "Doctor's daily free time"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                              [
                                              "09:00",
                                              "09:20",
                                              "09:40",
                                              "....."
                                            ]"""
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:."
                    ))
            })
    })
    public List<String> getAvailableSlots(
            @Parameter(description = "Doctor ID", example = "1") @PathVariable Long doctorId,
            @Parameter(description = "Date", example = "2025-07-05") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<LocalTime> slots = doctorService.getAvailable20MinuteSlots(doctorId, date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return slots.stream()
                .map(time -> time.format(formatter))
                .toList();
    }


    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTOR')")
    @PatchMapping("/{doctorId}/room")
    public ResponseEntity<?> changeRoom(@PathVariable("doctorId") Long id,
                                        @RequestParam(value = "room") String room) {

        doctorService.changeRoom(id, room);

        return ResponseEntity.ok("Room changed successfully");
    }
}
