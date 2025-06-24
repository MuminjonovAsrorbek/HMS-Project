package uz.dev.hmsproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorFilterDTO implements Serializable {

    private String fullName;

    private String username;

    private String specialityName;
}
