package com.bruno.trivia.exceptions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldMessage> fieldMessages = new ArrayList<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            fieldMessages.add(new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        ValidationErrorDTO dto = new ValidationErrorDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                "Os dados digitados são inválidos!",
                request.getRequestURI(),
                fieldMessages
        );
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardErrorDTO dto = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()

        );
        return ResponseEntity.status(status).body(dto);
    }
}
