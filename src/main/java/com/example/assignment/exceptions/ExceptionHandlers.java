package com.example.assignment.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    protected ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), responseHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<Object> handleInvalidValueException(InvalidValueException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), responseHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), responseHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static HttpHeaders responseHeaders() {
        HttpHeaders result = new HttpHeaders();
        result.setContentType(TEXT_PLAIN);
        return result;
    }
}


