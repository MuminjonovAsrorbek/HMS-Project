package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 11:30
 **/

@Getter
public class AppointmentDateExpiredException extends RuntimeException {
    private final HttpStatus status;

    public AppointmentDateExpiredException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }
}
