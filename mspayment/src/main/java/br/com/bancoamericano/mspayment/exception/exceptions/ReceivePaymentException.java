package br.com.bancoamericano.mspayment.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReceivePaymentException extends RuntimeException{

    public ReceivePaymentException(String message) {
        super(message);
    }
}
