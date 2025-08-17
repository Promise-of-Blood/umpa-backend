package promiseofblood.umpabackend.infrastructure.oauth;

import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2ProfileResponse;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2TokenResponse;

public interface Oauth2Strategy {

  /*
   * 1. 인증 URL 생성
   * 2. 인증 코드로 액세스 토큰 요청
   * 3-1. OpenID Connect 제공자일 경우, tokenUri 로 idToken, accessToken 요청
   * 3-2. OpenID Connect 제공자가 아닐 경우, accessToken 만 요청
   * 3. 프로필 정보 요청
   */

  String getAuthorizationUrl(Oauth2Provider oauth2Provider);

  Oauth2TokenResponse getToken(Oauth2Provider oauth2Provider, String authorizationCode);

  Oauth2ProfileResponse getOauth2UserProfile(
    Oauth2Provider oauth2Provider, String authorizationCode);

  Oauth2ProfileResponse getOauth2UserProfile(
    Oauth2Provider oauth2Provider, String externalAccessToken, String externalIdToken);
}
