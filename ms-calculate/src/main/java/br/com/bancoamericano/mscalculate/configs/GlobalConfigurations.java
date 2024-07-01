package br.com.bancoamericano.mscalculate.configs;

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
}
