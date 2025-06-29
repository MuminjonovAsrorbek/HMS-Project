package uz.dev.hmsproject.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 11:37
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRescheduleRequest {

    @NotNull
    private Long appointmentId;

    private Long newDoctorId;

    @NotNull
    private LocalDateTime newDateTime;

}
