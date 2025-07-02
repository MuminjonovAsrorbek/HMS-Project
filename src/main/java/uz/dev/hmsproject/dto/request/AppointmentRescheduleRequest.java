package uz.dev.hmsproject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Unique ID of the Appointment", examples = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long appointmentId;

    @Schema(description = "Unique ID of the Doctor", examples = "1")
    private Long newDoctorId;

    @NotNull
    @Schema(description = "New Appointment DateTime", examples = "2025-07-05T10:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime newDateTime;

}
