package br.com.bancoamericano.mspayment.model.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalculateRuleRequest {

    @Positive
    private Long categoryId;

    @PositiveOrZero
    private BigDecimal value;
}
