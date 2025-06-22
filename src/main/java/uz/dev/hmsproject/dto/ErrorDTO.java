package uz.dev.hmsproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private int code;

    private String message;

    List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
