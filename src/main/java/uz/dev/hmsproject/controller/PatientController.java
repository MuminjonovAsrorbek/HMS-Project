package uz.dev.hmsproject.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.dto.PatientSearchDTO;
import uz.dev.hmsproject.service.template.PatientService;

import java.util.List;


/**
 * Created by: suhrob
 */


@RestController
@RequestMapping("/api/patients")

public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize(value = "hasAuthority('PATIENTS_READ_ALL')")
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity
                .ok(patientService.getAll());
    }


    @PreAuthorize(value = "hasAuthority('PATIENTS_READ_BY_ID')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity
                .ok(patientService.getById(id));
    }


    @PreAuthorize(value = "hasAuthority('PATIENTS_CREATE')")
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientDTO patientDTO) {
        patientService.create(patientDTO);
        return ResponseEntity
                .ok("Patient created successfully");
    }


    @PreAuthorize(value = "hasAuthority('PATIENTS_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        patientService.update(id, patientDTO);
        return ResponseEntity
                .ok("Patient updated successfully");
    }


    @PreAuthorize(value = "hasAuthority('PATIENTS_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {

        patientService.delete(id);
        return ResponseEntity
                .ok("Patient deleted successfully");

    }



    @PreAuthorize(value = "hasAuthority('PATIENTS_SEARCH')")
    @PostMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestBody PatientSearchDTO searchDTO) {
        return ResponseEntity
                .ok(patientService
                        .search(searchDTO));
    }


}