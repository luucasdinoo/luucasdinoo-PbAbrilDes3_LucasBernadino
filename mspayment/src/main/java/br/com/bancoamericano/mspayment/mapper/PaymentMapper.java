package br.com.bancoamericano.mspayment.mapper;

import br.com.bancoamericano.mspayment.model.dto.PaymentResponseDto;
import br.com.bancoamericano.mspayment.model.entities.Payment;
import org.modelmapper.ModelMapper;

public class PaymentMapper {

    public static PaymentResponseDto toDto(Payment payment){
        return new ModelMapper().map(payment, PaymentResponseDto.class);
    }
}
