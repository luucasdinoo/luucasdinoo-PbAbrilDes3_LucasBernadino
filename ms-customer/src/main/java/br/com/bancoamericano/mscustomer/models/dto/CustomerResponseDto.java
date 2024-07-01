package br.com.bancoamericano.mscustomer.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerResponseDto implements Serializable {

    private String cpf;
    private String name;
    private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private String email;
    private Long points;
    private String url_photo;
}

