package uz.dev.hmsproject.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PreAuthorize(value = "hasAuthority('VIEW_PATIENTS')")
    @GetMapping
    public List<PatientDTO> getAllPatients() {

        return patientService.getAll();

    }

    @PreAuthorize(value = "hasAuthority('VIEW_PATIENT')")
    @GetMapping("/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {

        return patientService.getById(id);

    }


    @PreAuthorize(value = "hasAuthority('CREATE_PATIENTS')")
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientDTO patientDTO) {

        patientService.create(patientDTO);

        return ResponseEntity
                .ok("Patient created successfully");
    }


    @PreAuthorize(value = "hasAuthority('UPDATE_PATIENTS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {

        patientService.update(id, patientDTO);

        return ResponseEntity
                .ok("Patient updated successfully");
    }


    @PreAuthorize(value = "hasAuthority('DELETE_PATIENTS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {

        patientService.delete(id);

        return ResponseEntity
                .ok("Patient deleted successfully");

    }


    @PreAuthorize(value = "hasAuthority('PATIENTS_SEARCH')")
    @PostMapping("/search")
    public List<PatientDTO> searchPatients(@RequestBody PatientSearchDTO searchDTO) {

        return patientService.search(searchDTO);

    }

    // patient entitysidagi CRUD dagi xatoliklar va kamchiliklarni to'girlandi
}