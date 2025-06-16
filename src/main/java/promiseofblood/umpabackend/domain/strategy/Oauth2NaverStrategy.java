package promiseofblood.umpabackend.domain.strategy;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
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
  public Oauth2TokenResponse getToken(String code, Oauth2Provider oauth2Provider) {

    Oauth2TokenResponse response = restTemplate.getForObject(
      oauth2Provider.getTokenUri()
        + "?grant_type=authorization_code"
        + "&client_id=" + oauth2Provider.getClientId()
        + "&client_secret=" + oauth2Provider.getClientSecret()
        + "&code=" + code,
      Oauth2TokenResponse.class
    );

    return response;
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(String code,
    Oauth2Provider oauth2Provider) {

    Oauth2TokenResponse oauth2TokenResponse = getToken(code, oauth2Provider);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oauth2TokenResponse.getAccessToken());

    ResponseEntity<NaverProfileResponse> response = restTemplate.postForEntity(
      oauth2Provider.getProfileUri(),
      new HttpEntity<>(null, headers),
      NaverProfileResponse.class
    );

    return Oauth2ProfileResponse.builder()
      .externalAccessToken(oauth2TokenResponse.getIdToken())
      .externalAccessToken(oauth2TokenResponse.getAccessToken())
      .providerUid(response.getBody().getResponse().getId())
      .profileImageUrl(response.getBody().getResponse().getProfileImage())
      .username(response.getBody().getResponse().getNickname())
      .build();
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfileByIdToken(
    String externalIdToken,
    String externalAccessToken,
    Oauth2Provider oauth2Provider) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + externalAccessToken);

    ResponseEntity<NaverProfileResponse> response = restTemplate.postForEntity(
      oauth2Provider.getProfileUri(),
      new HttpEntity<>(null, headers),
      NaverProfileResponse.class
    );

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
