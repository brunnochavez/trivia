package com.bruno.trivia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI triviaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trivia API — Sistema de Venda para Hamburgueria")
                        .description("API REST para atendimento de balcão e delivery, cobrindo cardápio, formas de pagamento, bairros e configuração da empresa.")
                        .version("0.0.1-SNAPSHOT"));
    }
}