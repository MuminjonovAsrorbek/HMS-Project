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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.WorkSchedulerUpdateDto;
import uz.dev.hmsproject.dto.response.RespWorkSchedulerDTO;
import uz.dev.hmsproject.service.template.WorkSchedulerService;
import java.util.List;

@RestController
@RequestMapping("/api/work-schedulers")
@RequiredArgsConstructor
@Tag(name = "Work Schedule API", description = "Manage doctor work schedules")
@SecurityRequirement(name = "bearerAuth")
public class WorkSchedulerController {

    private final WorkSchedulerService workSchedulerService;

    @Operation(summary = "Get All Work Schedules by Doctor", description = "Get weekly work schedule of a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespWorkSchedulerDTO.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "dayOfWeek": 1,
                                        "startTime": "09:00",
                                        "endTime": "17:00"
                                      }
                                    ]
                                    """)))
    })
    @PreAuthorize("hasAuthority('VIEW_WORK_SCHEDULE')")
    @GetMapping("/user/{userId}")
    public List<RespWorkSchedulerDTO> getAllByUser(@Parameter(description = "Doctor ID", example = "5") @PathVariable Long userId) {
        return workSchedulerService.getByUserId(userId);
    }

    @Operation(summary = "Get Work Schedule by Doctor and Day", description = "Get work schedule of a doctor by day of week")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespWorkSchedulerDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "dayOfWeek": 1,
                                      "startTime": "09:00",
                                      "endTime": "17:00"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Schedule not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Work schedule not found")))
    })
    @PreAuthorize("hasAuthority('VIEW_WORK_SCHEDULE')")
    @GetMapping("/user/{userId}/day/{dayOfWeek}")
    public RespWorkSchedulerDTO getByUserIdAndDay(@Parameter(description = "Doctor ID", example = "5") @PathVariable Long userId,
                                                  @Parameter(description = "Day of week (1=Monday, ..., 7=Sunday)", example = "1") @PathVariable int dayOfWeek) {
        return workSchedulerService.getByUserIdAndDayOfWeek(userId, dayOfWeek);
    }

    @Operation(summary = "Create Work Schedule", description = "Create a work schedule for a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule created successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Work schedule created successfully")))
    })
    @PreAuthorize("hasAuthority('CREATE_WORK_SCHEDULES')")
    @PostMapping
    public ResponseEntity<?> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Schedule create DTO",
            required = true,
            content = @Content(schema = @Schema(implementation = WorkSchedulerDTO.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                              "userId": 5,
                              "dayOfWeek": 1,
                              "startTime": "09:00",
                              "endTime": "17:00"
                            }
                            """))
    ) @RequestBody @Valid WorkSchedulerDTO dto) {
        workSchedulerService.create(dto);
        return ResponseEntity.ok("Work schedule created successfully");
    }

    @Operation(summary = "Update Work Schedule", description = "Update the doctor’s work schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Work schedule updated successfully"))),
            @ApiResponse(responseCode = "404", description = "Schedule not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Entity not found with ID")))
    })
    @PreAuthorize("hasAuthority('UPDATE_WORK_SCHEDULES')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Parameter(description = "Work Schedule ID", example = "3") @PathVariable Long id,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "Update work schedule",
                                            required = true,
                                            content = @Content(
                                                    schema = @Schema(implementation = WorkSchedulerUpdateDto.class),
                                                    examples = @ExampleObject(value = """
                                                            {
                                                              "startTime": "10:00",
                                                              "endTime": "18:00"
                                                            }
                                                            """)
                                            )
                                    ) @RequestBody @Valid WorkSchedulerUpdateDto dto) {
        workSchedulerService.update(id, dto);
        return ResponseEntity.ok("Work schedule updated successfully");
    }

    @Operation(summary = "Delete Work Schedule", description = "Delete (soft delete or deactivate) doctor schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule deleted",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Work schedule deleted successfully"))),
            @ApiResponse(responseCode = "404", description = "Schedule not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Entity not found with ID")))
    })
    @PreAuthorize("hasAuthority('DELETE_WORK_SCHEDULES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "Work Schedule ID", example = "3") @PathVariable Long id) {
        workSchedulerService.delete(id);
        return ResponseEntity.ok("Work schedule deleted successfully");
    }
}