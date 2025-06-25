package com.inventario.inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//SWAGGER URL: http://localhost:8080/doc/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2025 Gestion de inventario")
                        .version("1.0")
                        .description("Documentación de la API para sistema de gestion de inventario"));
    }
}