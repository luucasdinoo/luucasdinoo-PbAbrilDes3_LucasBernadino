package br.com.bancoamericano.mspayment.services;

import br.com.bancoamericano.mspayment.clients.CalculateResourceClient;
import br.com.bancoamericano.mspayment.clients.CustomerResourceClient;
import br.com.bancoamericano.mspayment.exception.exceptions.DBCustomerNotFoundException;
import br.com.bancoamericano.mspayment.exception.exceptions.PaymentNotFoundException;
import br.com.bancoamericano.mspayment.exception.exceptions.ReceivePaymentException;
import br.com.bancoamericano.mspayment.model.dto.CalculateRuleRequest;
import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import br.com.bancoamericano.mspayment.model.dto.PaymentResponseDto;
import br.com.bancoamericano.mspayment.model.entities.Payment;
import br.com.bancoamericano.mspayment.queue.ReceivePaymentPublisher;
import br.com.bancoamericano.mspayment.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static br.com.bancoamericano.mspayment.common.PaymentConstants.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService service;

    @Mock
    private PaymentRepository repository;

    @Mock
    private CustomerResourceClient customerResourceClient;

    @Mock
    private CalculateResourceClient calculateResourceClient;

    @Mock
    private ReceivePaymentPublisher receivePaymentPublisher;

    @Test
    void getPaymentById_WithExistingId() {
        when(repository.findById(UUID)).thenReturn(Optional.of(PAYMENT));
        Payment sut = service.getPaymentById(UUID);

        assertNotNull(sut);
        assertNotNull(sut.getId());

        assertEquals(UUID, sut.getId());
        assertEquals(PAYMENT.getCustomerId(), sut.getCustomerId());
        assertEquals(PAYMENT.getTotalPayment(), sut.getTotalPayment());
        assertEquals(PAYMENT.getCreatedDate(), sut.getCreatedDate());

    }

    @Test
    void getPaymentById_WithNonExistingId() {
        java.util.UUID incorrect_uuid = java.util.UUID.randomUUID();
        when(repository.findById(incorrect_uuid)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getPaymentById(incorrect_uuid))
                .isInstanceOf(PaymentNotFoundException.class);
    }

    @Test
    void testReceivePayment_Success() {
        when(customerResourceClient.getCustomer(1L)).thenReturn(new ResponseEntity<>(CUSTOMER_DATA, HttpStatus.OK));
        when(calculateResourceClient.calculate(any(CalculateRuleRequest.class)))
                .thenReturn(new ResponseEntity<>(CALCULATE_RULE_RESPONSE, HttpStatus.OK));
        when(repository.save(any(Payment.class))).thenReturn(PAYMENT);

        PaymentResponseDto sut = service.receivePayment(PAYMENT_REQUEST_DTO);
        sut.setId(UUID);

        assertNotNull(sut);
        assertNotNull(sut.getCreatedDate());

        assertEquals(PAYMENT.getId(), sut.getId());
        assertEquals(PAYMENT.getCustomerId(), sut.getCustomerId());
        assertEquals(PAYMENT.getTotalPayment(), sut.getTotalPayment());
        assertEquals(CALCULATE_RULE_RESPONSE.getTotal(), sut.getPoints());
    }

    @Test
    public void testReceivePayment_customerNotFound() {
        when(customerResourceClient.getCustomer(99L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        DBCustomerNotFoundException exception = assertThrows(DBCustomerNotFoundException.class, () -> {
            service.receivePayment(PAYMENT_REQUEST_DTO_INVALID);
        });

        assertEquals("Customer with id 99 does not exist in the database", exception.getMessage());
    }

    @Test
    public void testReceivePayment_calculateFailure() {
        when(customerResourceClient.getCustomer(1L)).thenReturn(new ResponseEntity<>(CUSTOMER_DATA, HttpStatus.OK));
        when(calculateResourceClient.calculate(any(CalculateRuleRequest.class)))
                .thenThrow(new RuntimeException("Calculation error"));

        ReceivePaymentException exception = assertThrows(ReceivePaymentException.class, () -> {
            service.receivePayment(PAYMENT_REQUEST_DTO);
        });

        assertEquals("Calculation error", exception.getMessage());

    }
}
