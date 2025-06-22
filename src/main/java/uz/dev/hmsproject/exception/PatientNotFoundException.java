package uz.dev.hmsproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by: suhrob
 */

@Getter
public class PatientNotFoundException extends RuntimeException{

    private final HttpStatus status;

        public PatientNotFoundException(String message,HttpStatus status) {
            super(message);
            this.status=status;
        }

}
