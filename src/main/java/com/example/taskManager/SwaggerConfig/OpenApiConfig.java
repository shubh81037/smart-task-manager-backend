package com.example.taskManager.SwaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public OpenAPI baseOpenAPI()
    {
        return new OpenAPI().info(new Info().title("Smart Task-Manager API")
                                            .description("Spring Boot backend with JWT auth, pagination, sorting, admin features, and audit fields.")
                                            .version("v1.0.0"));
    }
}
