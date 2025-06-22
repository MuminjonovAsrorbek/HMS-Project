package uz.dev.hmsproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 5/28/25 15:06
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private int code;

    private String message;

    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(int status, String message) {
        this.code = status;
        this.message = message;
    }

}
