package com.pramodvaddiraju.taskpulse_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<Map<String,Object>> handleResourceError(ResourceNotFoundException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("time stamp: ", LocalDateTime.now());
        response.put("message: ", ex.getMessage());
        response.put("status: ", HttpStatus.NOT_FOUND.value());
        log.error("Error occured: ", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,Object>> handleValidationError(MethodArgumentNotValidException ex){
        Map<String,Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.error("error occured: ", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
