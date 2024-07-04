package br.com.bancoamericano.mspayment.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CalculateRuleResponse {

    private Long total;
}
