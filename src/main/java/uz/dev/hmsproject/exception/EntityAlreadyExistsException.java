package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public EntityAlreadyExistsException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
