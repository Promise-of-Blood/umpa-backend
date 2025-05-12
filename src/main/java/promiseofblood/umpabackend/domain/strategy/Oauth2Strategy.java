package promiseofblood.umpabackend.domain.strategy;

import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;

public interface Oauth2Strategy {

  String getAuthorizationUrl(Oauth2Provider oauth2Provider);

  String getAccessToken(String code, Oauth2Provider oauth2Provider);

  Oauth2ProfileResponse getUserInfo(String accessToken, Oauth2Provider oauth2Provider);

}
