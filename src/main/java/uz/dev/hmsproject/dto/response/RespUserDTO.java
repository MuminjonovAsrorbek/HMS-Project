package uz.dev.hmsproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response data transfer object for user")
public class RespUserDTO {

    @Schema(description = "Unique identifier of the user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Username of the user", example = "user1")
    private String username;

    @Schema(description = "Role name of the user", example = "ADMIN")
    private String roleName;

    @Schema(description = "Active of the user", example = "true")
    private boolean isActive = true;

}