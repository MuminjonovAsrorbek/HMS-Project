package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 5/28/25 15:07
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorDTO {

    private String field;

    private String message;

}
