package uz.dev.hmsproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 10:15
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReqDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Long roleId;
}
