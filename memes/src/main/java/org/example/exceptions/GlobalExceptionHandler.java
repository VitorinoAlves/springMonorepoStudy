package org.example.exceptions;

import org.example.dtos.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map <String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataExecption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(InvalidDataExecption ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );

        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleResourceNorFound(ResourceNotFound ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );

        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(ResourceBeingUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponseDto> handleResourceBeingUsedException(ResourceBeingUsedException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );

        return new ResponseEntity<>(errorResponseDto, status);
    }
}
