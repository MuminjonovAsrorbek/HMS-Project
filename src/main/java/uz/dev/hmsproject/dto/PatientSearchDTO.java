package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Created by: suhrob
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientSearchDTO {

    private String fullName;

    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998 and contain 13 digits")
    private String phoneNumber;
}
