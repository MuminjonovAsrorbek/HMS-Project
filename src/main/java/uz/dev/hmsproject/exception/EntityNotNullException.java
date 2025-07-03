package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 7/2/25 19:39
 **/

@Getter
public class EntityNotNullException extends RuntimeException {

    private final HttpStatus status;

    public EntityNotNullException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }

}
