package promiseofblood.umpabackend.domain.strategy;

import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;

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
  public String getAccessToken(String code, Oauth2Provider oauth2Provider) {
    // (POST) https://kauth.kakao.com/oauth/token
    // grant_type=authorization_code (고정)
    // client_id = provider.getClientId(); (객체가 가지고 있음)
    // redirect_uri = provider.getRedirectUri(); (객체가 가지고 있음)
    // code (외부에서 주입되어야 함)
    return "";
  }

  @Override
  public Oauth2ProfileResponse getUserInfo(String accessToken, Oauth2Provider oauth2Provider) {
    return null;
  }
}
