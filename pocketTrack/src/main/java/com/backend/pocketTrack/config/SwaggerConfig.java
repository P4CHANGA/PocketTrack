package com.backend.pocketTrack.config;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI customOpenAPI()
    {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
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
