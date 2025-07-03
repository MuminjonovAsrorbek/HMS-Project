package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Speciality}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityDTO implements Serializable {

    @Schema(description = "Unique id of the speciality", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Speciality name", example = "Cardiologist", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;

}