package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.response.StatisticsResponseDTO;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 17:01
 **/

public interface StatisticService {

    StatisticsResponseDTO getDailyStatistics(LocalDate startDate, LocalDate endDate);

}
