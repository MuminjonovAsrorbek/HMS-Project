package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.service.template.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAll(){
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable("id") Long id){
        return doctorService.getById(id);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody DoctorDTO doctorDTO){
        doctorService.create(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @RequestBody DoctorDTO doctorDTO){
        doctorService.update(id,doctorDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
