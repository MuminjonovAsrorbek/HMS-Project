package uz.dev.hmsproject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:29
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAppointmentDTO {

    @NotNull
    @Schema(description = "Unique ID of the Patient", examples = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long patientId;

    @NotNull
    @Schema(description = "Unique ID of the Doctor", examples = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long doctorId;

    @NotNull
    @Schema(description = "Appointment DateTime", examples = "2025-07-05T10:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime appointmentDateTime;

}
