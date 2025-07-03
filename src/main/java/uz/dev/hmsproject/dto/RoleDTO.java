package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.Permissions;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Role data transfer object")
public class RoleDTO {

    @Schema(description = "Unique identifier of the role", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of the role", example = "ADMIN")
    @NotBlank
    @Size(min = 3, max = 1000000000)
    private String name;

    @Schema(description = "Description of the role", example = "[\"VIEW_ROLE\",\"CREATE_ROLES\",\"UPDATE_ROLES\"]")
    @NotNull
    private List<Permissions> permissions;
}