package uz.dev.hmsproject.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.hmsproject.dto.response.ErrorDTO;
import uz.dev.hmsproject.dto.response.FieldErrorDTO;
import uz.dev.hmsproject.exception.*;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice(basePackages = "uz.dev.hmsproject")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityUniqueException.class)
    public ResponseEntity<ErrorDTO> handleEntityUniqueException(EntityUniqueException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = RoleInvalidPermissionsException.class)
    public ResponseEntity<ErrorDTO> handle(RoleInvalidPermissionsException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handle(UserAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = UserAlreadyExistsWithUsernameException.class)
    public ResponseEntity<ErrorDTO> handle(UserAlreadyExistsWithUsernameException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<FieldErrorDTO> fieldErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            fieldErrors.add(new FieldErrorDTO(fieldName, message));
        }

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Field not valid",
                fieldErrors
        );

        return ResponseEntity
                .status(400)
                .body(error);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> handle(RuntimeException e) {

        ErrorDTO error = new ErrorDTO(
                500,
                "Internal Server Error: " + e.getMessage()
        );

        return ResponseEntity
                .status(500)
                .body(error);
    }

    @ExceptionHandler(value = PasswordIncorrectException.class)
    public ResponseEntity<ErrorDTO> handle(PasswordIncorrectException e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handle(IllegalArgumentException e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDTO> handle(ObjectOptimisticLockingFailureException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.CONFLICT.value(),
                "This slot has already been booked. Please select another one."
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);

    }

    @ExceptionHandler(value = SendEmailErrorException.class)
    public ResponseEntity<ErrorDTO> handle(SendEmailErrorException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = AppointmentDateExpiredException.class)
    public ResponseEntity<ErrorDTO> handle(AppointmentDateExpiredException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDTO> handle(AuthorizationDeniedException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);

    }
}
