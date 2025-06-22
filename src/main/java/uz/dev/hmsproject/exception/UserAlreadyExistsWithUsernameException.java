package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserAlreadyExistsWithUsernameException extends RuntimeException {

    private HttpStatus status;

    public UserAlreadyExistsWithUsernameException(String message) {
        super(message);
    }

    public UserAlreadyExistsWithUsernameException(String username, HttpStatus status) {
        this("User with username: " + username + "already exists");
        this.status = status;
    }
}
