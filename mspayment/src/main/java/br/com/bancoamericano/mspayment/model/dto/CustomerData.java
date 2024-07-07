package br.com.bancoamericano.mspayment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CustomerData implements Serializable {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private Long points;
}

