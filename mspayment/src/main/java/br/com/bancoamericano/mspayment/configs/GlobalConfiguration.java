package br.com.bancoamericano.mspayment.configs;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class GlobalConfiguration {

    @Value("${mq.queues.payment}")
    public String paymentQueue;

    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @PostConstruct
    void timeZoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    @Bean
    Queue paymentQueue(){
        return new Queue(paymentQueue, true);
    }
}
