package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 7/2/25 12:40
 **/

@Getter
public class EntityNotWorkException extends RuntimeException {
    private final HttpStatus status;

    public EntityNotWorkException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
