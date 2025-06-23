package promiseofblood.umpabackend.domain.strategy;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import promiseofblood.umpabackend.core.exception.NotSupportedOauth2ProviderException;

@Component
@RequiredArgsConstructor
public class Oauth2StrategyFactory {

  private final Map<String, Oauth2Strategy> oauth2Strategies;

  public Oauth2Strategy getStrategy(String providerName) {
    Oauth2Strategy strategy = oauth2Strategies.get(
      this.resolveKey(providerName)
    );

    if (strategy == null) {
      throw new NotSupportedOauth2ProviderException("구현되지 않은 OAuth2 제공자입니다: " + providerName);
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
