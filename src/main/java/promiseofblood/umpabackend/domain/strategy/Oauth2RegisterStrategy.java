package promiseofblood.umpabackend.domain.strategy;


import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2UserInfoResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.Oauth2TokenResponse;

public interface Oauth2RegisterStrategy {

  Oauth2UserInfoResponse getOauth2UserInfo(Oauth2Provider oauth2Provider,
    Oauth2RegisterRequest oauth2RegisterRequest);

  String getLoginUrl(Oauth2Provider oauth2Provider);

  Oauth2TokenResponse getOauth2Token(Oauth2Provider oauth2Provider, String code);

}


