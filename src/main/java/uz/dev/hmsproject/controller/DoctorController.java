package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Getting the Doctors list to Filter",
    description = "Get a list of doctors to filter by speciality, fullName or username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",schema =
                    @Schema(implementation = DoctorResponseDTO.class),
                    examples = @ExampleObject(value =
                            "[\n" +
                                    "  {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"fullName\": \"Steve Big\",\n" +
                                    "    \"username\": \"BigBoy\",\n" +
                                    "    \"speciality\": \"Kardiolog\",\n" +
                                    "    \"priceList\": {\n" +
                                    "      \"id\": 1,\n" +
                                    "      \"specialityId\": 2,\n" +
                                    "      \"price\": 500_000,\n" +
                                    "      \"updateAt\": \"2025-07-03T09:37:21.804Z\"\n" +
                                    "    },\n" +
                                    "    \"room\": \"K25\"\n" +
                                    "  }\n" +
                                    "]"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id"))
            })
    })
    @PreAuthorize(value = "hasAuthority('FILTER_DOCTORS')")
    @GetMapping("/filter")
    public List<DoctorResponseDTO> filter(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get doctors by filter information",
    content = @Content(schema = @Schema(implementation = DoctorFilterDTO.class),mediaType = "application/json")) DoctorFilterDTO dto) {
        return doctorService.filter(dto);
    }

    @Operation(summary = "Get all doctors",
    description = "Retrieve a paginated list of all doctors")
    @PreAuthorize(value = "hasAuthority('VIEW_DOCTORS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return doctorService.getAllPaginated(page, size);
    }

    @Operation(summary = "Get Doctor by Id",
    description = "Retrieve a specific doctor by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = DoctorResponseDTO.class),
                    examples = @ExampleObject(value =
                            "[\n" +
                                    "  {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"fullName\": \"Steve Big\",\n" +
                                    "    \"username\": \"BigBoy\",\n" +
                                    "    \"speciality\": \"Kardiolog\",\n" +
                                    "    \"priceList\": {\n" +
                                    "      \"id\": 1,\n" +
                                    "      \"specialityId\": 2,\n" +
                                    "      \"price\": 500_000,\n" +
                                    "      \"updateAt\": \"2025-07-03T09:37:21.804Z\"\n" +
                                    "    },\n" +
                                    "    \"room\": \"K25\"\n" +
                                    "  }\n" +
                                    "]"))
            }),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id")))
    })
    @PreAuthorize(value = "hasAuthority('VIEW_DOCTOR')")
    @GetMapping("/{id}")
    public DoctorResponseDTO getById(@PathVariable("id") Long id) {
        return doctorService.getById(id);
    }

    @Operation(summary = "Create doctor",
    description = "Create and save a new doctor in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Doctor created successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "The doctor already exist with user,speciality or room"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id"))
            })
    })
    @PreAuthorize(value = "hasAuthority('CREATE_DOCTORS')")
    @PostMapping
    public ResponseEntity<?> create( @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create doctor",required = true, content = @Content(schema = @Schema(implementation = DoctorDTO.class), mediaType = "application/json"))
            @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.create(doctorDTO);
        return ResponseEntity.ok("Doctor created successfully");
    }

    @Operation(summary = "Update doctor",
    description = "Update an existing doctor in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Doctor updated successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "The doctor already exist with user,speciality or room"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id:"))
            })
    })
    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTORS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update doctor",required = true,
                                    content = @Content(schema = @Schema(implementation = DoctorDTO.class), mediaType = "application/json"))
                                    @RequestBody @Valid DoctorDTO doctorDTO) {
        doctorService.update(id, doctorDTO);
        return ResponseEntity.ok("Doctor updated successfully");
    }

    @Operation(summary = "Delete doctor",
    description = "Delete an existing doctor in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples =
                    @ExampleObject(value = "Doctor deleted successfully"))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id:"))
            })
    })
    @PreAuthorize(value = "hasAuthority('DELETE_DOCTORS')")
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


    @Operation(summary = "Change doctor's room",
    description = "Change doctor's room in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "Room changed successfully"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id:"))
            }),
            @ApiResponse(responseCode = "409", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Room already exist with this doctor"))
            })
    })
    @PreAuthorize(value = "hasAuthority('UPDATE_DOCTORS')")
    @PatchMapping("/{doctorId}/room")
    public ResponseEntity<?> changeRoom(@PathVariable("doctorId") Long id,
                                        @RequestParam(value = "room") String room) {
        doctorService.changeRoom(id, room);
        return ResponseEntity.ok("Room changed successfully");

    }
}
