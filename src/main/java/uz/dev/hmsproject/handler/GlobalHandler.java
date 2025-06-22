package uz.dev.hmsproject.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.hmsproject.dto.ErrorDTO;
import uz.dev.hmsproject.dto.FieldErrorDTO;
import uz.dev.hmsproject.exception.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "uz.dev.hmsproject")
public class GlobalHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldErrorDTO> fieldErrorDTOS = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(field, message);
            fieldErrorDTOS.add(fieldErrorDTO);
        }

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setFieldErrors(fieldErrorDTOS);
        errorDTO.setStatusCode(400);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRoomNotFound(RoomNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(SpecialityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleSpecialityNotfound(SpecialityNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFound(UserNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(RoleIsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRoleNotFound(RoleIsNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleDoctorNotFound(DoctorNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleRoleAlreadyExists(RoleAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(RoleInvalidPermissionsException.class)
    public ResponseEntity<ErrorDTO> handleRoleInvalidPermissions(RoleInvalidPermissionsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

}
