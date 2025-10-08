package cj.esanar.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "enf-sanar REST API",
                description = "REST API para la clinica de heridas Enf-Sanar",
                version = "1.0.0",
                contact = @Contact(
                        name = "Jefferson Chaustre",
                        email = "chaustrejefferson@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "Security By Tokens")
)
@SecurityScheme(
        name = "Security By Tokens",
        description = "Access token for API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWY"
)
public class SwaggerConfig {
}
