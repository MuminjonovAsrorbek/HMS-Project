package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.request.AppointmentFilterRequest;
import uz.dev.hmsproject.dto.request.AppointmentRescheduleRequest;
import uz.dev.hmsproject.dto.request.CreateAppointmentDTO;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.service.template.AppointmentService;
import uz.dev.hmsproject.service.template.FileService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
@Tag(name = "Appointment API", description = "A collection of full control of appointments")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final FileService fileService;

    @PreAuthorize("hasAuthority('APPOINTMENTS_CREATE')")
    @PostMapping
    @Operation(
            summary = "Create the Appointment",
            description = "Create new Appointment"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment created successfully"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointments can only be scheduled for today or future dates."
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:."
                    ))
            })
    })
    public ResponseEntity<?> createAppointment(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New appointment creation information", required = true,
            content = @Content(schema = @Schema(implementation = CreateAppointmentDTO.class), mediaType = "application/json")
    ) @RequestBody @Valid CreateAppointmentDTO createAppointmentDTO) {

        appointmentService.createAppointment(createAppointmentDTO);

        return ResponseEntity.ok("Appointment created successfully");

    }

    @Operation(
            summary = "Get Appointment by ID",
            description = "Get Appointment by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": 1, \"patient\": \"John Doe\", \"doctor\": \"Dr. Smith\", \"dateTime\": \"2024-06-10T10:10\", \"status\": \"SCHEDULED\" , \"room\": \"12\" , \"price\": \"256000\"}"
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:."
                    ))
            })
    })
    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {

        return appointmentService.getAppointmentById(id);

    }


    @Operation(
            summary = "Getting the Appointment lists to Filter",
            description = "Getting the Appointment lists to Filter"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDTO.class),
                            examples = @ExampleObject(
                                    value = "[ { \"id\": 1, \"patient\": \"John Doe\", \"doctor\": \"Dr. Smith\", \"dateTime\": \"2024-06-10T10:10\", \"status\": \"SCHEDULED\" , \"room\": \"12\" , \"price\": \"256000\"} ]"
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:."
                    ))
            })
    })
    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/filter")
    public List<AppointmentDTO> filterAppointments(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Get Appointments by filter information",
            content = @Content(schema = @Schema(implementation = AppointmentFilterRequest.class), mediaType = "application/json")
    ) AppointmentFilterRequest appointmentFilterRequest) {

        return appointmentService.filterAppointments(appointmentFilterRequest);

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_UPDATE')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Appointment",
            description = "Update Appointment"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment updated successfully"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "The doctor isn't active now"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:"
                    ))
            })
    })
    public ResponseEntity<?> updateAppointment(@Parameter(description = "Appointment ID", example = "1") @PathVariable Long id,
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                       description = "Update appointment information", required = true,
                                                       content = @Content(
                                                               schema = @Schema(implementation = CreateAppointmentDTO.class),
                                                               mediaType = "application/json")
                                               )
                                               @RequestBody @Valid CreateAppointmentDTO appointmentDTO) {

        appointmentService.updateAppointment(id, appointmentDTO);

        return ResponseEntity.ok("Appointment updated successfully");

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Appointment",
            description = "Appointment will not be deleted simply Canceled"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment deleted successfully"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "The doctor isn't active now"
                    ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Entity not found with ID:"
                    ))
            })
    })
    public ResponseEntity<?> deleteAppointment(@Parameter(example = "1") @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok("Appointment deleted successfully");

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_CHANGE_STATUS')")
    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Change Appointment Status",
            description = "Change Appointment Status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment status updated successfully"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Status cannot be null or empty"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment cannot be canceled less than 1 hour before it starts."
                    ))
            }),
    })
    public ResponseEntity<?> changeAppointmentStatus(@Parameter(description = "Appointment ID", example = "1") @PathVariable Long id,
                                                     @Parameter(description = "Appointment status", example = "CANCELED") @RequestParam String status) {

        appointmentService.changeAppointmentStatus(id, status);

        return ResponseEntity.ok("Appointment status updated successfully");

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/today-appointments")
    @Operation(
            summary = "Get Today Appointments",
            description = "Here the values are passed in Peagable view"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "size": 1,
                                              "totalElements": 1,
                                              "totalPages": 1,
                                              "hasNext": false,
                                              "hasPrevious": false,
                                              "object": [
                                                {
                                                  "id": 1,
                                                  "patient": "John Doe",
                                                  "doctor": "Dr. Smith",
                                                  "dateTime": "2024-06-10T10:10",
                                                  "status": "SCHEDULED",
                                                  "room": "12",
                                                  "price": "256000"
                                                }
                                              ]
                                            }
                                            """
                            ))
            })
    })
    public PageableDTO getTodayAppointments(@Parameter(description = "Page number", example = "1")
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) {

        return appointmentService.getTodayAppointments(page);

    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_READ')")
    @GetMapping("/daily-appointments/export")
    @Operation(
            summary = "Get Daily Appointments to Excel",
            description = "Daily Appointments list can be downloaded in excel format"
    )
    public void exportDailyAppointmentsToExcel(@Parameter(description = "Enter the required date.", example = "2025-07-05")
                                               @RequestParam(required = false) LocalDate date,
                                               HttpServletResponse response) throws IOException {

        if (Objects.isNull(date)) date = LocalDate.now();

        List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String fileName = "appointments_" + date + ".xlsx";

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        Workbook workbook = fileService.generateExcelReport(appointments, date);

        workbook.write(response.getOutputStream());

        workbook.close();
    }

    @PreAuthorize("hasAuthority('APPOINTMENTS_UPDATE')")
    @PutMapping("/reschedule")
    @Operation(
            summary = "Update Appointment",
            description = "This update allows you to assign a patient to a new doctor or change the date."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointment rescheduled successfully"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Can change Appointment only in Scheduled mode"
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "Appointments can only be scheduled for today or future dates."
                    ))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "The doctor isn't active now"
                    ))
            }),
            @ApiResponse(responseCode = "409", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "This time slot is already booked"
                    ))
            }),
    })
    public ResponseEntity<?> reschedule(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Update appointment information", required = true,
            content = @Content(
                    schema = @Schema(implementation = AppointmentRescheduleRequest.class),
                    mediaType = "application/json"))
                                        @RequestBody @Valid AppointmentRescheduleRequest dto) {

        appointmentService.reschedule(dto);

        return ResponseEntity.ok("Appointment rescheduled successfully");

    }
}