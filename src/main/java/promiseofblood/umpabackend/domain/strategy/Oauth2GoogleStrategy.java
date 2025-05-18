package promiseofblood.umpabackend.domain.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.GoogleProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2TokenResponse;

@Component
@RequiredArgsConstructor
public class Oauth2GoogleStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

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

    Oauth2TokenResponse response = restTemplate.postForObject(
      oauth2Provider.getTokenUri(),
      new HttpEntity<>(body, headers),
      Oauth2TokenResponse.class
    );

    return response.getAccessToken();
  }

  @Override
  public Oauth2ProfileResponse getUserInfo(String accessToken, Oauth2Provider oauth2Provider) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);

    ResponseEntity<GoogleProfileResponse> response = restTemplate.exchange(
      oauth2Provider.getProfileUri(),
      HttpMethod.GET,
      new HttpEntity<>(headers),
      GoogleProfileResponse.class
    );

    return Oauth2ProfileResponse.builder()
      .providerUid(response.getBody().getSub())
      .profileImageUrl(response.getBody().getPicture())
      .username(response.getBody().getName())
      .build();
  }
}
