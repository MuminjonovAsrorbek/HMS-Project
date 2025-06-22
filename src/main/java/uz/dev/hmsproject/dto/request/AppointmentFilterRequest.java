package uz.dev.hmsproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.AppointmentStatus;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 16:01
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentFilterRequest {

    private Long doctorId;
    private Long patientId;
    private AppointmentStatus status;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
