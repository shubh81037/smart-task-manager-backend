package com.example.taskManager.SwaggerConfig;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth" ,
                bearerFormat = "JWT" ,
                type = SecuritySchemeType.HTTP ,
                scheme = "bearer")
public class OpenAPISecurityConfig
{

}
