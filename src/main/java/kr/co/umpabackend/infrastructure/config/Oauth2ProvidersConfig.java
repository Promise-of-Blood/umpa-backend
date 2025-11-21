package kr.co.umpabackend.infrastructure.config;

import java.util.List;
import kr.co.umpabackend.application.exception.NotSupportedOauth2ProviderException;
import kr.co.umpabackend.domain.vo.Oauth2Provider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2ProvidersConfig {

  private List<Oauth2Provider> providers;

  public Oauth2Provider get(String providerName) {
    return providers.stream()
        .filter(provider -> provider.getName().equalsIgnoreCase(providerName))
        .findFirst()
        .orElseThrow(
            () ->
                new NotSupportedOauth2ProviderException("지원하지 않는 OAuth2 제공자입니다: " + providerName));
  }
}
