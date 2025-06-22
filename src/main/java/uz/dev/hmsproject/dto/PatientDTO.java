package uz.dev.hmsproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * DTO for {@link uz.dev.hmsproject.entity.Patient}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO implements Serializable {

   private Long id;

    private String fullName;

    private Date birthDate;

    private String phoneNumber;

    private String address;

    private Timestamp createdAt;


}