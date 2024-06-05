package com.kartik.noteapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kartik.noteapp.dtos.ErrorDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionalHandler {
    @ExceptionHandler(value = EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> handleConflict(Exception e, HttpServletRequest req) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDTO(req.getRequestURI(), HttpStatus.CONFLICT.value(),
                e.getMessage(), LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = WeakPasswordException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorDTO> handleUnProcessableEntity(Exception e, HttpServletRequest req) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDTO(req.getRequestURI(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(), LocalDateTime.now()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = BadCredentialException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorDTO> handleUnAuthorized(Exception e, HttpServletRequest req) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDTO(req.getRequestURI(), HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(), LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
    }
}
