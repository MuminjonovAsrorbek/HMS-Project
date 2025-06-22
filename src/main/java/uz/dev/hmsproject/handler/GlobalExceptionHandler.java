package uz.dev.hmsproject.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.hmsproject.dto.ErrorDTO;
import uz.dev.hmsproject.dto.ErrorFieldsKeeperDTO;
import uz.dev.hmsproject.dto.FieldErrorDTO;
import uz.dev.hmsproject.exception.UserAlreadyExistsException;
import uz.dev.hmsproject.exception.UserAlreadyExistsWithUsernameException;
import uz.dev.hmsproject.exception.UserNotFoundException;


import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice(basePackages = "uz.pdp")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(UserNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getMessage(),
                e.getStatus().value()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handle(UserAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getMessage(),
                e.getStatus().value()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }
    @ExceptionHandler(value = UserAlreadyExistsWithUsernameException.class)
    public ResponseEntity<ErrorDTO> handle(UserAlreadyExistsWithUsernameException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getMessage(),
                e.getStatus().value()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFieldsKeeperDTO> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldErrorDTO> fieldErrors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            fieldErrors.add(new FieldErrorDTO(fieldName, message));
        }
        ErrorFieldsKeeperDTO errorDTO = new ErrorFieldsKeeperDTO(
                400,
                "field not valid",
                fieldErrors
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> handle(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO(
                "Server error please try again later",
                500
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
