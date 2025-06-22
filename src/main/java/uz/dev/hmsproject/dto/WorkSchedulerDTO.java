package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkSchedulerDTO implements Serializable {

    private Long id;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Day of week must not be null")
    private Integer dayOfWeek;

    @NotNull(message = "Start time must not be null")
    private LocalTime startTime;

    @NotNull(message = "End time must not be null")
    private LocalTime endTime;
}