package uz.dev.hmsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorFieldsKeeperDTO {
    private int status;
    private String message;
    private List<FieldErrorDTO> fieldErrors;

}
