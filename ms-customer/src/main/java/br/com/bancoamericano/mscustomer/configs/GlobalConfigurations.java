package br.com.bancoamericano.mscustomer.configs;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

public class GlobalConfigurations {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @PostConstruct
    void timeZoneConfi(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
