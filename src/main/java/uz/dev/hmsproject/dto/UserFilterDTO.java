package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "DTO for filtering users",
        title = "User Filter DTO")
public class UserFilterDTO {


    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Username of the user", example = "johndoe")
    private String username;

    @Schema(description = "Unique identifier of the user", example = "5")
    @Positive
    private Integer id;

    @Schema(description = "Indicates if the user is active", example = "true")
    private boolean active;


    @Schema(description = "Role of the user", example = "ADMIN")
    private String role;


}
