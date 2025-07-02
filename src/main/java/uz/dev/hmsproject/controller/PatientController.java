package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.dto.PatientSearchDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.PatientService;

import java.util.List;

/**
 * Created by: suhrob
 */

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Endpoints for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get all patients (paginated)")
    @ApiResponse(responseCode = "200", description = "List of patients returned")
    @PreAuthorize("hasAuthority('VIEW_PATIENTS')")
    @GetMapping
    public PageableDTO getAllPatients(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return patientService.getAllPaginated(page, size);
    }

    @Operation(summary = "Get patient by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PreAuthorize("hasAuthority('VIEW_PATIENT')")
    @GetMapping("/{id}")
    public PatientDTO getPatientById(
            @Parameter(description = "Patient ID", example = "1")
            @PathVariable Long id) {
        return patientService.getById(id);
    }

    @Operation(summary = "Create new patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasAuthority('CREATE_PATIENTS')")
    @PostMapping
    @RequestBody(
            description = "Patient information to create",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            name = "New patient",
                            value = """
                                    {
                                      "fullName": "Suhrob Qurbonmurodov",
                                      "birthDate": "1995-08-10",
                                      "phoneNumber": "+998901234567",
                                      "email": "suhrob@example.com",
                                      "address": "Tashkent, Uzbekistan"
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<?> createPatient(
            @Valid @org.springframework.web.bind.annotation.RequestBody PatientDTO patientDTO) {
        patientService.create(patientDTO);
        return ResponseEntity.ok("Patient created successfully");
    }

    @Operation(summary = "Update existing patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient successfully updated"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PreAuthorize("hasAuthority('UPDATE_PATIENTS')")
    @PutMapping("/{id}")
    @RequestBody(
            description = "Updated patient data",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            name = "Updated patient",
                            value = """
                                    {
                                      "fullName": "Ali Valiyev",
                                      "birthDate": "1985-05-05",
                                      "phoneNumber": "+998909876543",
                                      "email": "ali@example.com",
                                      "address": "Andijon, Uzbekistan"
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<?> updatePatient(
            @Parameter(description = "Patient ID", example = "1")
            @PathVariable Long id,
            @org.springframework.web.bind.annotation.RequestBody PatientDTO patientDTO) {
        patientService.update(id, patientDTO);
        return ResponseEntity.ok("Patient updated successfully");
    }

    @Operation(summary = "Delete a patient by ID (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PreAuthorize("hasAuthority('DELETE_PATIENTS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(
            @Parameter(description = "Patient ID", example = "1")
            @PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

    @Operation(summary = "Search patients by filters")
    @ApiResponse(responseCode = "200", description = "List of matching patients")
    @PreAuthorize("hasAuthority('PATIENTS_SEARCH')")
    @PostMapping("/search")
    @RequestBody(
            description = "Search filters",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            name = "Search Example",
                            value = """
                                    {
                                      "fullName": "Ali",
                                      "phoneNumber": "+99890"
                                    }
                                    """
                    )
            )
    )
    public List<PatientDTO> searchPatients(
            @org.springframework.web.bind.annotation.RequestBody PatientSearchDTO searchDTO) {
        return patientService.search(searchDTO);
    }

    @Operation(summary = "Get patient's appointment history (paginated)")
    @ApiResponse(responseCode = "200", description = "Patient appointment history returned")
    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/{patientId}/appointments")
    public PageableDTO getPatientHistory(
            @Parameter(description = "Patient ID", example = "1")
            @PathVariable Long patientId,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return patientService.getPatientHistory(patientId, page);
    }
}
