package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.DoctorService;


@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTORS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "hasAuthority('DELETE_DOCTORS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
