package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public DoctorNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
