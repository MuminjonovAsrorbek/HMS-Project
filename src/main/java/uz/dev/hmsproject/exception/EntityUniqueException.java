package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 14:10
 **/

@Getter
public class EntityUniqueException extends RuntimeException {

    private final HttpStatus status;

    public EntityUniqueException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
