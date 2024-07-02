package br.com.bancoamericano.mspayment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerData implements Serializable {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private Long points;
}

