package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpecialityAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public SpecialityAlreadyExistsException(String name, HttpStatus status) {
        super(name);
        this.status = status;
    }

}
