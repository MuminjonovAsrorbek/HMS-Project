package uz.dev.hmsproject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Unique ID of the Doctor", examples = "1")
    private Long doctorId;

    @Schema(description = "Unique ID of the Patient", examples = "1")
    private Long patientId;

    @Schema(description = "Status of the Appointment", examples = "SCHEDULED")
    private AppointmentStatus status;

    @Schema(description = "Date From", examples = "2025-07-05")
    private LocalDate dateFrom;

    @Schema(description = "Date To", examples = "2025-07-05")
    private LocalDate dateTo;

}
