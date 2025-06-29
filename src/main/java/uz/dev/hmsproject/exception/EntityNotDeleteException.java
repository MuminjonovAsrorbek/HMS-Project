package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 15:06
 **/

@Getter
public class EntityNotDeleteException extends RuntimeException {

    private final HttpStatus status;

    public EntityNotDeleteException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
