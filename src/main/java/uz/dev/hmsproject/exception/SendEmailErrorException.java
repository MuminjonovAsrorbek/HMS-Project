package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 10:43
 **/

@Getter
public class SendEmailErrorException extends RuntimeException {
    private final HttpStatus status;

    public SendEmailErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }

}
