package br.com.bancoamericano.mscustomer.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerUpdateDto {


    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Email
    private String email;

}
