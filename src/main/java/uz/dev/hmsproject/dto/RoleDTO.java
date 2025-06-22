package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.NotBlank;
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

    private String name;

    private List<Permissions> permissions;
}