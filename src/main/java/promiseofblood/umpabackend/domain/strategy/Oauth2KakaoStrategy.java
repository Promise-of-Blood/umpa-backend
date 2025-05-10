package promiseofblood.umpabackend.domain.strategy;

import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;

@Component
public class Oauth2KakaoStrategy implements Oauth2Strategy {

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
      + "?client_id=" + oauth2Provider.getClientId()
      + "&redirect_uri=" + oauth2Provider.getRedirectUri()
      + "&response_type=code";
  }

  @Override
  public String getAccessToken(String code, String redirectUri) {
    return "";
  }

  @Override
  public String getUserInfo(String accessToken) {
    return "";
  }
}
