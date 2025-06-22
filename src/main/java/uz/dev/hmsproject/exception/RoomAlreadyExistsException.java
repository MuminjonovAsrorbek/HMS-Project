package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoomAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public RoomAlreadyExistsException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
