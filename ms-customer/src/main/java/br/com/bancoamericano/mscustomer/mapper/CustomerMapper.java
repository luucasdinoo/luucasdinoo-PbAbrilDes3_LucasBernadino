package br.com.bancoamericano.mscustomer.mapper;

import br.com.bancoamericano.mscustomer.models.dto.CustomerCreateDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerResponseDto;
import br.com.bancoamericano.mscustomer.models.entities.Customer;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class CustomerMapper {

    public static CustomerResponseDto toDto (Customer customer) {
        return new ModelMapper().map(customer, CustomerResponseDto.class);
    }

    public static Customer toCustomer (CustomerCreateDto dto) {
        return new ModelMapper().map(dto, Customer.class);
    }
}
