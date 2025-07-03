package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Speciality}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityCreationDTO implements Serializable {

    @Schema(description = "Unique id of the speciality", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(description = "Speciality name", example = "Cardiologist",
    requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(description = "Speciality price", example = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;
}