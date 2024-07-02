package br.com.bancoamericano.mspayment.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DBCustomerNotFoundException extends RuntimeException {

    public DBCustomerNotFoundException(String message) {
        super(message);
    }
}
