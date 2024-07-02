package br.com.bancoamericano.mspayment.queue;

import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import br.com.bancoamericano.mspayment.util.UtilityClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceivePaymentPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue paymentQueue;

    public void receivePayment(PaymentRequestDto data) throws JsonProcessingException {
        String json = UtilityClass.convertIntoJason(data);
        rabbitTemplate.convertAndSend(paymentQueue.getName(), json);
    }
}
