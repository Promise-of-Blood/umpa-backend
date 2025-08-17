package promiseofblood.umpabackend.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class OpenApiConfig {

  @Bean
  public OpenAPI umpaOpenAPI() {
    return new OpenAPI()
        .info(
            new Info().title("음파 서버 API").version("0.1.0").description("음- 하면서 내쉬고, 파- 하면서 들이쉬세요."))
        .components(getComponents())
        .addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
  }

  private Components getComponents() {
    SecurityScheme bearerTokenScheme =
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");

    return new Components().securitySchemes(Map.of("Bearer Token", bearerTokenScheme));
  }
}
