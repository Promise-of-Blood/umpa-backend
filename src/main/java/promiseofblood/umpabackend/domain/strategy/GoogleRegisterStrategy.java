package promiseofblood.umpabackend.domain.strategy;

import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2UserInfoResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.Oauth2TokenResponse;

@Component
public class GoogleRegisterStrategy implements Oauth2RegisterStrategy {

  @Override
  public Oauth2UserInfoResponse getOauth2UserInfo(Oauth2Provider oauth2Provider,
    Oauth2RegisterRequest oauth2RegisterRequest) {
    return null;
  }

  @Override
  public String getLoginUrl(Oauth2Provider oauth2Provider) {
    return "GOOGLE";
  }

  @Override
  public Oauth2TokenResponse getOauth2Token(Oauth2Provider oauth2Provider, String code) {
    return null;
  }
}
