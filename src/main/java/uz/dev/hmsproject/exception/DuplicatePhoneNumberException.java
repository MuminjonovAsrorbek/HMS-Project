package uz.dev.hmsproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Created by: suhrob
 */


public class DuplicatePhoneNumberException extends ResponseStatusException {
    public DuplicatePhoneNumberException(String phoneNumber) {
        super(HttpStatus.CONFLICT, "This phone number already exists: " + phoneNumber);
    }
}
