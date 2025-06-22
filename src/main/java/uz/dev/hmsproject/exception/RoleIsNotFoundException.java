package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoleIsNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public RoleIsNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
