package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class InvalidRecaptchaTokenException extends RuntimeException {

    private final HttpStatus status;

    public InvalidRecaptchaTokenException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
