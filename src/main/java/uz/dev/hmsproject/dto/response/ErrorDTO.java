package uz.dev.hmsproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard error response body")
public class ErrorDTO {

    @Schema(description = "HTTP status code", example = "500")
    private int code;

    @Schema(description = "Detailed error message", example = "Unexpected server error occurred during processing.")
    private String message;

    @Schema(description = "List of field-level validation errors (if any)")
    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(int status, String message) {
        this.code = status;
        this.message = message;
    }
}
