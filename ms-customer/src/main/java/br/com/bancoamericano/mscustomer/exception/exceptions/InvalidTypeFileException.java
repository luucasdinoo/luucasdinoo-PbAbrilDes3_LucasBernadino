package br.com.bancoamericano.mscustomer.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTypeFileException extends RuntimeException {

    public InvalidTypeFileException(String message) {
        super(message);
    }
}
