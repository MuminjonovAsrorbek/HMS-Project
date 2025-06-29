package uz.dev.hmsproject.dto;

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
public class RoleDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 1000000000)
    private String name;

    @NotNull
    private List<Permissions> permissions;
}