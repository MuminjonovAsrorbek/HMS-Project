package uz.dev.hmsproject.dto.response;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RespWorkSchedulerDTO implements Serializable {

    private Long id;


    //user details
    private String fullName;
    private String roleName;

    //change dayOfWeek to String for better readability
    private String dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;
}