package br.com.bancoamericano.mscalculate.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class GlobalConfigurations {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @PostConstruct
    void timeZoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API with java 17 and spring boot 3 for challenge 3")
                        .version("v1")
                        .description("Restful API with java 17 and spring boot 3 for challenge 3 -> ms-calculate Microservice")
                        .termsOfService("")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        ));
    }
}
