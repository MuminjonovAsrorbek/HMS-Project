package uz.dev.hmsproject.dto;

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

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;
}