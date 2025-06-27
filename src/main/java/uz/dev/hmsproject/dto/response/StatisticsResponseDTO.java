package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.projection.DailyAppointment;
import uz.dev.hmsproject.projection.DoctorActivity;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 17:18
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsResponseDTO {

    private Long totalAppointments;

    private Long totalPatients;

    private Long totalCanceledAppointments;

    private List<DailyAppointment> dailyAppointments;

    private List<DoctorActivity> doctorActivities;

}
