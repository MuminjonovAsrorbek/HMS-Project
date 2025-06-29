package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.SpecialityCreationDTO;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.SpecialityService;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialityService specialityService;

    @PreAuthorize(value = "hasAuthority('VIEW_SPECIALTIES')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return specialityService.getAllByPage(page, size);

    }

    @PreAuthorize(value = "hasAuthority('VIEW_SPECIALTY')")
    @GetMapping("/{id}")
    public SpecialityDTO getById(@PathVariable("id") Long id) {

        return specialityService.getById(id);

    }

    @PreAuthorize(value = "hasAuthority('CREATE_SPECIALTIES')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SpecialityCreationDTO specialityDTO) {

        specialityService.create(specialityDTO);

        return ResponseEntity.ok("Speciality created successfully");

    }

    @PreAuthorize(value = "hasAuthority('UPDATE_SPECIALTIES')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid SpecialityDTO specialityDTO) {

        specialityService.update(id, specialityDTO);

        return ResponseEntity.ok("Speciality updated successfully");

    }

    @PreAuthorize(value = "hasAuthority('DELETE_SPECIALTIES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        specialityService.delete(id);

        return ResponseEntity.ok("Speciality deleted successfully");

    }
}
