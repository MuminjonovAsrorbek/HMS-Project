package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class StatisticController {

    private final StatisticService statisticService;


    @PreAuthorize("hasAuthority('VIEW_STATISTICS')")
    @GetMapping("/daily-appointments")
    public StatisticsResponseDTO getDailyStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return statisticService.getDailyStatistics(startDate, endDate);

    }

}
