package uz.dev.hmsproject.dto.request;

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
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDateTime appointmentDateTime;

}
