package uz.dev.hmsproject.dto;

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

    private Long id;

    @NotBlank
    private String name;


}