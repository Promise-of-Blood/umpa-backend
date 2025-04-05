package promiseofblood.umpabackend.domain.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.NaverProfileResponse;
import promiseofblood.umpabackend.dto.external.NaverTokenResponse;
import promiseofblood.umpabackend.dto.external.Oauth2UserInfoResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.Oauth2TokenResponse;

@Component
@RequiredArgsConstructor
public class NaverRegisterStrategy implements Oauth2RegisterStrategy {

  private final RestTemplate restTemplate;

  @Override
  public Oauth2UserInfoResponse getOauth2UserInfo(Oauth2Provider oauth2Provider,
    Oauth2RegisterRequest oauth2RegisterRequest) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oauth2RegisterRequest.getAccessToken());
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<NaverProfileResponse> response = restTemplate.exchange(
      oauth2Provider.getProfileUri(),
      HttpMethod.GET,
      request,
      NaverProfileResponse.class
    );
    NaverProfileResponse naverProfileResponse = response.getBody();

    return Oauth2UserInfoResponse.builder()
      .socialId(naverProfileResponse.getResponse().getId())
      .profileImageUrl(naverProfileResponse.getResponse().getProfileImage())
      .accessToken(oauth2RegisterRequest.getAccessToken())
      .refreshToken(oauth2RegisterRequest.getRefreshToken())
      .build();
  }

  @Override
  public String getLoginUrl(Oauth2Provider oauth2Provider) {

    String clientId = oauth2Provider.getClientId();
    String redirectUri = oauth2Provider.getRedirectUris().get(0);
    String baseUrl = oauth2Provider.getLoginUrl();

    return baseUrl +
      "?response_type=code" +
      "&client_id=" + clientId +
      "&redirect_uri=" + redirectUri;
  }

  @Override
  public Oauth2TokenResponse getOauth2Token(Oauth2Provider oauth2Provider, String code) {
    String grantType = "authorization_code";
    String clientId = oauth2Provider.getClientId();
    String clientSecret = oauth2Provider.getClientSecret();
    String BaseUrl = oauth2Provider.getTokenUri();

    NaverTokenResponse response = restTemplate.getForObject(
      BaseUrl + "?grant_type=" + grantType +
        "&client_id=" + clientId +
        "&client_secret=" + clientSecret +
        "&code=" + code,
      NaverTokenResponse.class
    );

    return Oauth2TokenResponse
      .builder()
      .accessToken(response.getAccessToken())
      .refreshToken(response.getRefreshToken())
      .build();
  }
}
