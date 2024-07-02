package br.com.bancoamericano.mspayment.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RuleData implements Serializable {

    private Long id;
    private String category;
    private Integer parity;

}
