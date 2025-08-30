package promiseofblood.umpabackend.infrastructure.oauth;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.application.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2ProfileResponse;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2TokenResponse;

@Component
@ToString
@RequiredArgsConstructor
public class Oauth2KakaoStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
        + "?client_id="
        + oauth2Provider.getClientId()
        + "&redirect_uri="
        + oauth2Provider.getRedirectUri()
        + "&response_type=code"
        + "&scope=openid";
  }

  @Override
  public Oauth2TokenResponse getToken(Oauth2Provider oauth2Provider, String authorizationCode) {
    // x-www-form-urlencoded 바디 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", oauth2Provider.getClientId());
    body.add("redirect_uri", oauth2Provider.getRedirectUri());
    body.add("client_secret", oauth2Provider.getClientSecret());
    body.add("code", authorizationCode);

    // 헤더 설정 (Content-Type: application/x-www-form-urlencoded)
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return restTemplate.postForObject(
        oauth2Provider.getTokenUri(), new HttpEntity<>(body, headers), Oauth2TokenResponse.class);
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(
      Oauth2Provider oauth2Provider, String authorizationCode) {

    Oauth2TokenResponse oauth2TokenResponse = this.getToken(oauth2Provider, authorizationCode);

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
  public Oauth2ProfileResponse getOauth2UserProfile(
      Oauth2Provider oauth2Provider, String externalAccessToken, String externalIdToken) {

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
    JwkProvider provider =
        new JwkProviderBuilder("https://kauth.kakao.com").cached(10, 7, TimeUnit.DAYS).build();

    try {
      Jwk jwk = provider.get(jwtOrigin.getKeyId());
      Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
      JWTVerifier verifier = JWT.require(algorithm).ignoreIssuedAt().build();
      return verifier.verify(externalIdToken);
    } catch (Exception e) {
      throw new UnauthorizedException("ID 토큰이 유효하지 않습니다.");
    }
  }
}
