package com.ingsoft.trueque.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Trueque backend",
                description = "Es una plataforma destinada a la comunidad universitaria. Su propósito es facilitar el intercambio de artículos de segunda mano, como ropa, libros y útiles académicos, promoviendo una economía circular dentro de la comunidad universitaria.\n",
                version = "1.0.0",
                contact = @Contact(
                        name = "universidad del magdalena",
                        url = "www.unimagdalena.edu.co",
                        email = "cids@unimagdalena.edu.co"
                )
        ),
        servers = {
                @Server(
                        description = "Dev server",
                        url = "http://localhost:8181/api/v1"
                ),
                @Server(
                        description = "Prod server",
                        url = "http://trueque-estudiantil.unimagdalena.com"
                )
        },
        security = {
               @SecurityRequirement(
                    name = "security token"
               )
        }
)
@SecurityScheme(
        name = "security token",
        description = "acces token for my api",
        type = SecuritySchemeType.HTTP,
        paramName = "Authorization",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
