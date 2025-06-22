package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:49
 **/

@Getter
public class PasswordIncorrectException extends RuntimeException {
    private final HttpStatus status;

    public PasswordIncorrectException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }
}
