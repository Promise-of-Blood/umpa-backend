package promiseofblood.umpabackend.domain.strategy;


import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2TokenResponse;
import java.security.interfaces.RSAPublicKey;

@Component
@RequiredArgsConstructor
public class Oauth2KakaoStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
      + "?client_id=" + oauth2Provider.getClientId()
      + "&redirect_uri=" + oauth2Provider.getRedirectUri()
      + "&response_type=code"
      + "&scope=openid";
  }

  @Override
  public Oauth2TokenResponse getToken(String code, Oauth2Provider oauth2Provider) {
    // x-www-form-urlencoded 바디 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", oauth2Provider.getClientId());
    body.add("redirect_uri", oauth2Provider.getRedirectUri());
    body.add("client_secret", oauth2Provider.getClientSecret());
    body.add("code", code);

    // 헤더 설정 (Content-Type: application/x-www-form-urlencoded)
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return restTemplate.postForObject(
      oauth2Provider.getTokenUri(),
      new HttpEntity<>(body, headers),
      Oauth2TokenResponse.class
    );
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(String code, Oauth2Provider oauth2Provider) {

    Oauth2TokenResponse oauth2TokenResponse = this.getToken(code, oauth2Provider);

    String externalIdToken = oauth2TokenResponse.getIdToken();
    String externalAccessToken = oauth2TokenResponse.getAccessToken();
    String externalRefreshToken = oauth2TokenResponse.getRefreshToken();

    DecodedJWT jwt = validateExternalIdToken(externalIdToken);

    return Oauth2ProfileResponse.builder()
      .externalIdToken(externalIdToken)
      .externalAccessToken(externalAccessToken)
      .providerUid(jwt.getClaim("sub").asString())
      .profileImageUrl(jwt.getClaim("picture").asString())
      .username(jwt.getClaim("nickname").asString())
      .build();
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfileByIdToken(
    String externalIdToken,
    String externalAccessToken,
    Oauth2Provider oauth2Provider) {

    DecodedJWT jwt = validateExternalIdToken(externalIdToken);

    return Oauth2ProfileResponse.builder()
      .externalIdToken(externalIdToken)
      .externalAccessToken(externalAccessToken)
      .providerUid(jwt.getClaim("sub").asString())
      .profileImageUrl(jwt.getClaim("picture").asString())
      .username(jwt.getClaim("nickname").asString())
      .build();

  }

  private DecodedJWT validateExternalIdToken(String externalIdToken) {
    DecodedJWT jwtOrigin = JWT.decode(externalIdToken);
    JwkProvider provider = new JwkProviderBuilder("https://kauth.kakao.com")
      .cached(10, 7, TimeUnit.DAYS)
      .build();

    try {
      Jwk jwk = provider.get(jwtOrigin.getKeyId());
      Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
      JWTVerifier verifier = JWT.require(algorithm).ignoreIssuedAt()
        .build();
      return verifier.verify(externalIdToken);
    } catch (Exception e) {
      throw new RuntimeException("Kakao externalIdToken 검증 실패: " + e.getMessage(), e);
    }

  }
}
