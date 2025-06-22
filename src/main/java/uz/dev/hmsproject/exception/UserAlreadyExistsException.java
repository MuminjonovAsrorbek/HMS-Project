package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private HttpStatus status;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(Long id, HttpStatus status) {
        this("User with id " + id + "already exists");
        this.status = status;
    }
}
