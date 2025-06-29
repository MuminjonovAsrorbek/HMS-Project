package uz.dev.hmsproject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecaptchaResponseDTO {

    private boolean success;
    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<String> getErrorCodes() { return errorCodes; }
    public void setErrorCodes(List<String> errorCodes) { this.errorCodes = errorCodes; }
}
