package uz.dev.hmsproject.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a role that already exists
 */
public class RoleAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public RoleAlreadyExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}