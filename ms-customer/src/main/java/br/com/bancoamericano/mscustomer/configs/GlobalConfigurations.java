package br.com.bancoamericano.mscustomer.configs;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class GlobalConfigurations {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @PostConstruct
    void timeZoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
