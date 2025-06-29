package uz.dev.hmsproject.dto;

import lombok.*;

/**
 * Created by: suhrob
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientSearchDTO {
    private String fullName;
    private String phoneNumber;
}
