package com.AjAkrampoor.Academy.shared.config;


import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }
}
