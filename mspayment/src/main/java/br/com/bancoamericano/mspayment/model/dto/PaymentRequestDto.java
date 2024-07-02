package br.com.bancoamericano.mspayment.model.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentRequestDto implements Serializable {

    @Positive
    private Long customerId;
    @Positive
    private Long categoryId;
    @PositiveOrZero
    private BigDecimal total;
}
