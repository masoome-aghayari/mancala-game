package com.mancala.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${application.version}")
    private String appVersion;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Mancala Game")
                .description("This application provides a simple mancala game without AI")
                .version(appVersion));
    }
}