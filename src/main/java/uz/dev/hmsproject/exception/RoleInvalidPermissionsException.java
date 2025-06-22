package uz.dev.hmsproject.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a role has invalid or missing permissions
 */
public class RoleInvalidPermissionsException extends RuntimeException {

    private final HttpStatus status;

    public RoleInvalidPermissionsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}