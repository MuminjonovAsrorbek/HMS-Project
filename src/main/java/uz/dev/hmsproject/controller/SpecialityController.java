package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.service.SpecialityServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialityServiceImpl specialityService;

    @GetMapping
    public List<SpecialityDTO> getAll() {
        return specialityService.getAll();
    }

    @GetMapping("/{id}")
    public SpecialityDTO getById(@PathVariable("id") Long id) {
        return specialityService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SpecialityDTO specialityDTO) {
        specialityService.create(specialityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid SpecialityDTO specialityDTO) {
        specialityService.update(id, specialityDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        specialityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
