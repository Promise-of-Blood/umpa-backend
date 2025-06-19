package promiseofblood.umpabackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
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
    return new OpenAPI().components(getComponents()).info(
        new io.swagger.v3.oas.models.info.Info().title("음파 서버 API").version("0.1.0")
          .description("음- 하면서 내쉬고, 파- 하면서 들이쉬세요."))
      .addSecurityItem(new SecurityRequirement().addList("Password Flow"));

  }

  private Components getComponents() {
    SecurityScheme passwordFlowScheme = new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(
      new OAuthFlows().password(new OAuthFlow().tokenUrl("http://localhost:8080/oauth/token")));

    return new Components().securitySchemes(Map.of("Password Flow", passwordFlowScheme));
  }
}
