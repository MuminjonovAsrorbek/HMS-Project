package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 16:19
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRespDTO {

    private Long appointmentId;

    private String doctorFullName;

    private String doctorSpecialty;

    private String patientFullName;

    private String patientEmail;

    private String patientPhone;

    private LocalDateTime appointmentDateTime;

    private String roomNumber;

    private BigDecimal price;

    private AppointmentStatus status;

}
