package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpecialityNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public SpecialityNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
