package br.com.bancoamericano.mscustomer.infra.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentSubscriber {

    @RabbitListener(queues = "${mq.queues.payment}")
    public void receivePayment(@Payload String payload){
        System.out.println(payload);
    }
}
