package br.com.bancoamericano.mspayment.controller;

import br.com.bancoamericano.mspayment.exception.exceptions.ReceivePaymentException;
import br.com.bancoamericano.mspayment.mapper.PaymentMapper;
import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import br.com.bancoamericano.mspayment.model.dto.PaymentResponseDto;
import br.com.bancoamericano.mspayment.model.entities.Payment;
import br.com.bancoamericano.mspayment.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> receivePayment(@RequestBody PaymentRequestDto payment) {
        try {
            PaymentResponseDto responseDto = paymentService.receivePayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }
        catch (ReceivePaymentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID paymentId){
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(PaymentMapper.toDto(payment));
    }

    @GetMapping("{userId}")
    public ResponseEntity ipl3(@PathVariable String userId){
        return null;
        //TODO
    }
}
