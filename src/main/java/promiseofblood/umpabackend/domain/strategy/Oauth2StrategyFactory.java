package promiseofblood.umpabackend.domain.strategy;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;

import java.util.Map;
import promiseofblood.umpabackend.exception.NotSupportedOauth2ProviderException;

@Component
@RequiredArgsConstructor
public class Oauth2StrategyFactory {

  private final Map<String, Oauth2Strategy> strategies;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;

  public Oauth2Strategy create(String providerName) {

    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    if (provider == null) {
      throw new NotSupportedOauth2ProviderException(providerName + "는(은) 지원되지 않는 oauth2 제공자입니다.");
    }

    String strategyKey = resolveKey(providerName);
    Oauth2Strategy strategy = strategies.get(strategyKey);
    if (strategy == null) {
      throw new IllegalArgumentException("No strategy available for provider: " + providerName);
    }

    return strategy;
  }

  private String resolveKey(String providerName) {
    // example: "naver" -> "oauth2NaverStrategy"
    return "oauth2"
      + providerName.substring(0, 1).toUpperCase()
      + providerName.substring(1)
      + "Strategy";
  }
}
