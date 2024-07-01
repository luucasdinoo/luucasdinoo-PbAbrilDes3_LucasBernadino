package br.com.bancoamericano.mscustomer.exception;

import br.com.bancoamericano.mscustomer.exception.exceptions.CustomerNotFoundException;
import br.com.bancoamericano.mscustomer.exception.exceptions.RequiredObjectIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExceptions(Exception ex, WebRequest request) {
        var response = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(RequiredObjectIsNullException ex, WebRequest request) {
        var response = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(CustomerNotFoundException ex, WebRequest request) {
        var response = new ExceptionResponse(new Date(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
