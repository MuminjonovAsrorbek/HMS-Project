package uz.dev.hmsproject.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class  RoomNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public RoomNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
