package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Role}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    private List<Permission> permissions;
}