package uz.dev.hmsproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Patient}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Full name must not be blank")
    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;

    @NotNull(message = "Birth date is required")
    private Date birthDate;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998 and contain 13 digits")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    @Size(min = 5, message = "Email must be at least 5 characters long")
    @Pattern(regexp = ".*@gmail\\.com$", message = "Only @gmail.com addresses are allowed")
    private String email;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 5, message = "Address must be at least 5 characters long")
    private String address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;
}
