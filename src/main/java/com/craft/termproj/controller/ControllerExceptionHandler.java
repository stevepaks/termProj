package com.craft.termproj.controller;

import com.craft.termproj.exception.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidArgumentException.class)
    protected ResponseEntity<String> handleArgumentException(InvalidArgumentException ex) {

        return new ResponseEntity<>(ex.getErrorCode().getMessage(), HttpStatus.BAD_REQUEST);
    }

}
