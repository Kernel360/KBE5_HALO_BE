package com.kernel.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Halocare API",
                version = "v1",
                description = "Halocare 백엔드 API 문서"
        ),
        servers = {
                @Server(url = "http://halocare.site", description = "Production Server"),
                @Server(url = "https://dev.halocare.site", description = "Development Server"),
                @Server(url = "http://localhost:8080", description = "Local Server")
        }
)
public class SwaggerConfig {
}
