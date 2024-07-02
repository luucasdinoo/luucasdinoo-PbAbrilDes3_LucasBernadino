package br.com.bancoamericano.mspayment.util;

import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilityClass {

    public static String convertIntoJason(PaymentRequestDto dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dados);
    }
}
