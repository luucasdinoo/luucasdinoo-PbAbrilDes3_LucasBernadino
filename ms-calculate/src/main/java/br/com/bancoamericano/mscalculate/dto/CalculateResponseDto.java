package br.com.bancoamericano.mscalculate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalculateResponseDto {

    private BigDecimal total;
}
