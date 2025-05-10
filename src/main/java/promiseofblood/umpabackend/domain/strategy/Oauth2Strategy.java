package promiseofblood.umpabackend.domain.strategy;

import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;

public interface Oauth2Strategy {

  String getAuthorizationUrl(Oauth2Provider oauth2Provider);

  String getAccessToken(String code, String redirectUri);

  String getUserInfo(String accessToken);

}
