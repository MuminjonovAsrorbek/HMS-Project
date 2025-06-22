package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public DoctorAlreadyExistsException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
