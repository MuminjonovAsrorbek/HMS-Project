package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Doctor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO implements Serializable {

    @Schema(description = "Unique id of the doctor", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Full name of the doctor", example = "<NAME>", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "Speciality id of the doctor", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long specialityId;

    @Schema(description = "Room id of the doctor", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roomId;
}