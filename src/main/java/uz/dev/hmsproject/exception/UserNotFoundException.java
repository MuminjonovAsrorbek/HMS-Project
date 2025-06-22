package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserNotFoundException extends RuntimeException {

    private HttpStatus status;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id, HttpStatus status) {
        this("User with id " + id + " not found");
        this.status = status;
    }
}
