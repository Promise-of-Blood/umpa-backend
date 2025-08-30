package promiseofblood.umpabackend.infrastructure.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class SwaggerConfig {

  public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
    List<MediaType> supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
    supportedMediaTypes.add(new MediaType("application", "octet-stream"));
    converter.setSupportedMediaTypes(supportedMediaTypes);
  }
}
