package com.virtualgame.exception;

import com.virtualgame.exception.exceptions.AlreadyExistsException;
import com.virtualgame.exception.exceptions.EmptyFieldException;
import com.virtualgame.exception.exceptions.InvalidInputException;
import com.virtualgame.exception.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseError> handleAlreadyExists(AlreadyExistsException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ResponseError> handleEmptyField(EmptyFieldException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ResponseError> handleInvalidInput(InvalidInputException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ValidationResponseError> handleValidationException(
            WebExchangeBindException e) {
        List<ValidationError> errors =
                e.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
        ValidationResponseError body = new ValidationResponseError(errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGeneric(Exception e) {
        ResponseError body = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
