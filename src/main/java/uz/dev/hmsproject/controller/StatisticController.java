package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.dto.response.StatisticsResponseDTO;
import uz.dev.hmsproject.service.template.StatisticService;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 16:52
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics API", description = "A collection of full control of statistics")
public class StatisticController {

    private final StatisticService statisticService;


    @PreAuthorize("hasAuthority('VIEW_STATISTICS')")
    @GetMapping("/daily-appointments")
    @Operation(
            summary = "Get Daily Statistics",
            description = "Get Daily Appointments Statistics"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "totalAppointments": 0,
                                              "totalPatients": 0,
                                              "totalCanceledAppointments": 0,
                                              "dailyAppointments": [
                                                {
                                                  "count": 0,
                                                  "date": "2025-07-02"
                                                }
                                              ],
                                              "doctorActivities": [
                                                {
                                                  "appointmentCount": 0,
                                                  "doctorFullName": "string",
                                                  "doctorId": 0
                                                }
                                              ]
                                            }"""
                            ))
            })
    })
    public StatisticsResponseDTO getDailyStatistics(@Parameter(example = "2025-07-05")
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @Parameter(example = "2025-07-09")
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return statisticService.getDailyStatistics(startDate, endDate);

    }
}