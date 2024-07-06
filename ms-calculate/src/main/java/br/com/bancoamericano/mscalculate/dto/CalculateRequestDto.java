package br.com.bancoamericano.mscalculate.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CalculateRequestDto {

    @Positive
    private Long categoryId;

    @PositiveOrZero
    private BigDecimal value;
}
