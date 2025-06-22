package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.entity.Doctor;

import java.io.Serializable;

/**
 * DTO for {@link Doctor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO implements Serializable {

    private Long id;

    private String fullName;

    private String username;

    private String speciality;

    private PriceListDTO priceList;

    private String room;
}