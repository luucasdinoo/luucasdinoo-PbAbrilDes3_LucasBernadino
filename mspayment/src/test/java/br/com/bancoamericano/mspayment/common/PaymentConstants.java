package br.com.bancoamericano.mspayment.common;

import br.com.bancoamericano.mspayment.model.dto.CalculateRuleResponse;
import br.com.bancoamericano.mspayment.model.dto.CustomerData;
import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import br.com.bancoamericano.mspayment.model.entities.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentConstants {

    public static final LocalDateTime localDateTime = LocalDateTime.now();
    public static final BigDecimal bigDecimal = new BigDecimal("1000.0");
    public static final UUID UUID = java.util.UUID.randomUUID();
    public static final Payment PAYMENT = new Payment(UUID,1L, bigDecimal , localDateTime);

    public static final PaymentRequestDto PAYMENT_REQUEST_DTO = new PaymentRequestDto(1L, 1L, bigDecimal);
    public static final PaymentRequestDto PAYMENT_REQUEST_DTO_INVALID = new PaymentRequestDto(99L, 1L, bigDecimal);

    public static final CustomerData CUSTOMER_DATA = new CustomerData(1L, "656.159.110-00", "Customer", "email@gmail.com", 0L);

    public static final CalculateRuleResponse CALCULATE_RULE_RESPONSE = new CalculateRuleResponse(10000L);



}
