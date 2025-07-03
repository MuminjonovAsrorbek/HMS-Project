package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDTO {


    private String search;

    private String fullName;

    private String username;

    @Positive
    private Integer id;

    private boolean active;


    private String role;


}
