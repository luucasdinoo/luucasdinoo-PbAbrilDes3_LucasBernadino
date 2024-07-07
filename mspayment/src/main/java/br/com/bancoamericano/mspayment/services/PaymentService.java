package br.com.bancoamericano.mspayment.services;

import br.com.bancoamericano.mspayment.clients.CalculateResourceClient;
import br.com.bancoamericano.mspayment.clients.CustomerResourceClient;
import br.com.bancoamericano.mspayment.exception.exceptions.DBCustomerNotFoundException;
import br.com.bancoamericano.mspayment.exception.exceptions.PaymentNotFoundException;
import br.com.bancoamericano.mspayment.exception.exceptions.ReceivePaymentException;
import br.com.bancoamericano.mspayment.model.dto.*;
import br.com.bancoamericano.mspayment.model.entities.Payment;
import br.com.bancoamericano.mspayment.queue.ReceivePaymentPublisher;
import br.com.bancoamericano.mspayment.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReceivePaymentPublisher receivePaymentPublisher;
    private final CalculateResourceClient calculateResourceClient;
    private final CustomerResourceClient customerResourceClient;

    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new PaymentNotFoundException("Payment not found"));
    }

    public PaymentResponseDto receivePayment(PaymentRequestDto data) {
        ResponseEntity<CustomerData> customer = customerResourceClient.getCustomer(data.getCustomerId());
        if(!customer.getStatusCode().is2xxSuccessful())
            throw new DBCustomerNotFoundException(
                    String.format("Customer with id %s does not exist in the database", data.getCustomerId()));
        try{
            CalculateRuleRequest ruleRequest = new CalculateRuleRequest(data.getCategoryId(), data.getTotal());
            ResponseEntity<CalculateRuleResponse> ruleResponse = calculateResourceClient.calculate(ruleRequest);
            receivePaymentPublisher.receivePayment(data);

            LocalDateTime now = LocalDateTime.now();
            Payment payment = new Payment(null, data.getCustomerId(), data.getTotal(), now);
            paymentRepository.save(payment);

            customerResourceClient.updatePoints(data.getCustomerId(), Objects.requireNonNull(ruleResponse.getBody()).getTotal());

            return PaymentResponseDto.builder()
                    .id(payment.getId())
                    .customerId(payment.getCustomerId())
                    .totalPayment(payment.getTotalPayment())
                    .points(Objects.requireNonNull(ruleResponse.getBody()).getTotal())
                    .createdDate(payment.getCreatedDate())
                    .build();
        }
        catch (Exception e){
            throw new ReceivePaymentException(e.getMessage());
        }
    }
}
