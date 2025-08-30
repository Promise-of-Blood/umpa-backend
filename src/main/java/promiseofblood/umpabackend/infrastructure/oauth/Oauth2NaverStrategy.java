package promiseofblood.umpabackend.infrastructure.oauth;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.infrastructure.oauth.dto.NaverProfileResponse;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2ProfileResponse;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2TokenResponse;

@Component
@ToString
@RequiredArgsConstructor
public class Oauth2NaverStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
        + "?client_id="
        + oauth2Provider.getClientId()
        + "&redirect_uri="
        + oauth2Provider.getRedirectUri()
        + "&response_type=code";
  }

  @Override
  public Oauth2TokenResponse getToken(Oauth2Provider oauth2Provider, String authorizationCode) {

    return restTemplate.getForObject(
        oauth2Provider.getTokenUri()
            + "?grant_type=authorization_code"
            + "&client_id="
            + oauth2Provider.getClientId()
            + "&client_secret="
            + oauth2Provider.getClientSecret()
            + "&code="
            + authorizationCode,
        Oauth2TokenResponse.class);
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(
      Oauth2Provider oauth2Provider, String authorizationCode) {

    Oauth2TokenResponse oauth2TokenResponse = this.getToken(oauth2Provider, authorizationCode);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oauth2TokenResponse.getAccessToken());

    ResponseEntity<NaverProfileResponse> response =
        restTemplate.postForEntity(
            oauth2Provider.getProfileUri(),
            new HttpEntity<>(null, headers),
            NaverProfileResponse.class);

    return Oauth2ProfileResponse.builder()
        .externalAccessToken(oauth2TokenResponse.getIdToken())
        .externalAccessToken(oauth2TokenResponse.getAccessToken())
        .providerUid(response.getBody().getResponse().getId())
        .profileImageUrl(response.getBody().getResponse().getProfileImage())
        .username(response.getBody().getResponse().getNickname())
        .build();
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(
      Oauth2Provider oauth2Provider, String externalAccessToken, String externalIdToken) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + externalAccessToken);

    ResponseEntity<NaverProfileResponse> response =
        restTemplate.postForEntity(
            oauth2Provider.getProfileUri(),
            new HttpEntity<>(null, headers),
            NaverProfileResponse.class);

    NaverProfileResponse.Response profileResponse = response.getBody().getResponse();

    return Oauth2ProfileResponse.builder()
        .externalAccessToken(externalIdToken)
        .externalAccessToken(externalAccessToken)
        .providerUid(profileResponse.getId())
        .profileImageUrl(profileResponse.getProfileImage())
        .username(profileResponse.getNickname())
        .build();
  }
}
