package uz.dev.hmsproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Full user data transfer object")
public class UserDTO {

    @Schema(description = "Unique identifier of the user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Full name of the user", example = "John Doe")
    @NotBlank
    private String fullName;

    @Schema(description = "Username of the user", example = "user1")
    @NotBlank
    private String username;

    @Schema(description = "Password of the user", example = "11111111")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Role ID of the user", example = "1")
    @NotNull
    private Long roleId;

    @JsonIgnore
    private boolean isActive = true;
}