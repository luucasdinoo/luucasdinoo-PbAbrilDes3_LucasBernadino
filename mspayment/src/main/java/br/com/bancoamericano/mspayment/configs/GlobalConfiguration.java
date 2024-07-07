package br.com.bancoamericano.mspayment.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API with java 17 and spring boot 3 for challenge 3")
                        .version("v1")
                        .description("Restful API with java 17 and spring boot 3 for challenge 3 ms-payment Microservice")
                        .termsOfService("")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        ));
    }
}
