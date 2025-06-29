package uz.dev.hmsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Doctor}
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO implements Serializable {

    private Long id;

    private String username;

    private String speciality;

    private String room;
}
