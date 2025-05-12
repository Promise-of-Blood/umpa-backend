package promiseofblood.umpabackend.domain.strategy;

import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;

@Component
public class Oauth2GoogleStrategy implements Oauth2Strategy {

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
      + "?client_id=" + oauth2Provider.getClientId()
      + "&redirect_uri=" + oauth2Provider.getRedirectUri()
      + "&response_type=code"
      + "&scope=https://www.googleapis.com/auth/userinfo.profile";
  }

  @Override
  public String getAccessToken(String code, Oauth2Provider oauth2Provider) {
    //
    return "";
  }

  @Override
  public Oauth2ProfileResponse getUserInfo(String accessToken, Oauth2Provider oauth2Provider) {
    return null;
  }
}
