package promiseofblood.umpabackend.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;

@Getter
@Configuration
@ConfigurationProperties
public class Oauth2ProvidersConfig {

  private final Map<String, Oauth2Provider> oauth2Providers = new HashMap<>();

  public Oauth2Provider getOauth2ProviderByName(String providerName) {
    return oauth2Providers.get(providerName.toLowerCase());
  }

}
