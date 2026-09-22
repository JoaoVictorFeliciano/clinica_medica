package com.joaovictor.clinica_medica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Clínica Médica API")
                                .version("1.0")
                                .description("""
                                        API para gerenciamento de pacientes,
                                        médicos e consultas de uma clínica médica.
                                        """)
                                .contact(
                                        new Contact()
                                                .name("João Victor, Lázaro e Wallif")
                                )
                                .license(
                                        new License()
                                                .name("Uso Acadêmico")
                                )
                );
    }
}