package com.backend.pocketTrack.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("PocketTrack API")
                        .description("API REST de PocketTrack")
                        .contact(new Contact()
                                .name("Ivan Garcia")
                                .email("ivagarala@alu.edu.gva.es")
                                .url("https://portal.edu.gva.es/iesmaciaabela/"))
                        .version("1.0")
                );
    }
}
