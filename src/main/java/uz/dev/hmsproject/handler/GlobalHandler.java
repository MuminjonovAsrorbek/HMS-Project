package uz.dev.hmsproject.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.hmsproject.dto.ErrorDTO;
import uz.dev.hmsproject.exception.*;

@RestControllerAdvice(basePackages = "uz.dev.hmsproject")
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRoomNotFound(RoomNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO,e.getStatus());
    }

    @ExceptionHandler(SpecialityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleSpecialityNotfound(SpecialityNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO,e.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFound(UserNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO,e.getStatus());
    }

    @ExceptionHandler(RoleIsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRoleNotFound(RoleIsNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO,e.getStatus());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleDoctorNotFound(DoctorNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(e.getStatus().value());
        return new ResponseEntity<>(errorDTO,e.getStatus());
    }




}
