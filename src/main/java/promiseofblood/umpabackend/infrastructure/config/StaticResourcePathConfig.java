package promiseofblood.umpabackend.infrastructure.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class StaticResourcePathConfig {

  private final Path svgPath = Paths.get("/static/svg");
}
