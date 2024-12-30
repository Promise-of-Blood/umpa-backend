package promiseofblood.umpabackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    servers = {
      @Server(url = "http://localhost:8080", description = "로컬 서버"),
      @Server(url = "https://tgoddessana.duckdns.org", description = "프로덕션 서버")
    })
public class OpenApiConfig {

  @Bean
  public OpenAPI umpaOpenAPI() {
    return new OpenAPI()
        .info(
            new io.swagger.v3.oas.models.info.Info()
                .title("음파 서버 API")
                .version("1.0")
                .description("음- 하면서 내쉬고, 파- 하면서 들이쉬세요."));
  }
}
