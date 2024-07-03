package br.com.bancoamericano.mscustomer.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerCreateDto implements Serializable {

    @CPF
    @Size(min = 14, max = 14)
    private String cpf;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Pattern(regexp = "MALE|FEMALE")
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    @Email
    private String email;

    @NotBlank
    private String base64Photo;
}
