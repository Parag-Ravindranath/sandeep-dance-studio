package com.project.sds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class handleAllException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e, WebRequest request)
    {
        errorHandling error=new errorHandling(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}
