package uz.dev.hmsproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Represents a validation error for a specific field")
public class FieldErrorDTO {

    @Schema(description = "The name of the field that failed validation", example = "username")
    private String field;

    @Schema(description = "The validation error message", example = "Username must not be blank")
    private String message;
}
