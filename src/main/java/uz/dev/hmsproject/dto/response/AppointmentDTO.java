package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 15:51
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {

    private Long id;

    private String patient;

    private String doctor;

    private LocalDateTime dateTime;

    private AppointmentStatus status;

    private String room;

    private BigDecimal price;

}
