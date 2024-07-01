package br.com.bancoamericano.mscalculate.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuleResponseDto {

    private Long id;
    private String category;
    private Integer parity;

}
