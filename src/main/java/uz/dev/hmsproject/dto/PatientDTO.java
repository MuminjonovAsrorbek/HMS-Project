package uz.dev.hmsproject.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String fullName;

    @NotNull
    private Date birthDate;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;


}