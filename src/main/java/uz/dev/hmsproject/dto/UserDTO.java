package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.dev.hmsproject.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Long roleId;

    private boolean isActive = true;
}