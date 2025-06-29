package uz.dev.hmsproject.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 17:34
 **/

@OpenAPIDefinition(
        info = @Info(
                title = "Spring 6 Swagger With Annotation Config",
                version = "${api.version}",
                contact = @Contact(
                        name = "Mavlonov Mehroj", email = "john.lgd65@gmail.com", url = "https://github.com/mehrojbek"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://springdoc.org"),
                termsOfService = "http://swagger.io/terms/",
                description = "Spring 6 Swagger Simple Application"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring 6 Wiki Documentation", url = "https://springshop.wiki.github.org/docs"
        ),
        servers = {

                @Server(
                        url = "http://10.10.4.0:8080",
                        description = "Production-Server"
                ),
                @Server(
                        url = "http://localhost:8080",
                        description = "Production-Localhost"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)

@SecurityScheme(
        name = "bearerAuth", // Xavfsizlik sxemasiga berilgan nom (yuqoridagi bilan bir xil bo'lishi kerak)
        type = SecuritySchemeType.HTTP, // Sxema turi
        scheme = "bearer", // Sxema nomi (JWT uchun "bearer")
        bearerFormat = "JWT", // Token formati haqida ma'lumot (ixtiyoriy, lekin tavsiya etiladi)
        description = "Autentifikatsiya uchun JWT tokenni 'Bearer ' prefiksi bilan kiriting. Masalan: Bearer eyJhbGciOiJI..."
)
public class OpenApiConfig {
}
