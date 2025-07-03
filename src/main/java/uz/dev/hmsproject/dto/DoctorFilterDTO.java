package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorFilterDTO implements Serializable {

    @Schema(description = "Full name of the doctor",examples = "Steve Mark")
    private String fullName;

    @Schema(description = "Username of the doctor", examples = "steve")
    private String username;

    @Schema(description = "Speciality name of the doctor", examples = "Cardiologist")
    private String specialityName;
}
