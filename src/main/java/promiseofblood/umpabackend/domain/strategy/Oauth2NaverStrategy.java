package promiseofblood.umpabackend.domain.strategy;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.NaverProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2TokenResponse;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;

@Component
@RequiredArgsConstructor
public class Oauth2NaverStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
      + "?client_id=" + oauth2Provider.getClientId()
      + "&redirect_uri=" + oauth2Provider.getRedirectUri()
      + "&response_type=code";
  }

  @Override
  public String getAccessToken(String code, Oauth2Provider oauth2Provider) {

    Oauth2TokenResponse response = restTemplate.getForObject(
      oauth2Provider.getTokenUri()
        + "?grant_type=authorization_code"
        + "&client_id=" + oauth2Provider.getClientId()
        + "&client_secret=" + oauth2Provider.getClientSecret()
        + "&redirect_uri=" + oauth2Provider.getRedirectUri()
        + "&code=" + code,
      Oauth2TokenResponse.class
    );

    return response.getAccessToken();
  }

  @Override
  public Oauth2ProfileResponse getUserInfo(String accessToken, Oauth2Provider oauth2Provider) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);

    ResponseEntity<NaverProfileResponse> response = restTemplate.postForEntity(
      oauth2Provider.getProfileUri(),
      new HttpEntity<>(null, headers),
      NaverProfileResponse.class
    );

    return Oauth2ProfileResponse.builder()
      .externalAccessToken(accessToken)
      .providerUid(response.getBody().getResponse().getId())
      .profileImageUrl(response.getBody().getResponse().getProfileImage())
      .username(response.getBody().getResponse().getNickname())
      .build();
  }
}
