package br.com.bancoamericano.mscalculate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RuleCreateDto {

    @NotBlank
    private String category;

    @PositiveOrZero
    private Integer parity;
}
