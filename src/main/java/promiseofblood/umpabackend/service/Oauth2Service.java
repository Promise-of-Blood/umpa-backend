package promiseofblood.umpabackend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.external.NaverProfileResponse;
import promiseofblood.umpabackend.dto.external.NaverTokenResponse;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.domain.Oauth2Provider;
import promiseofblood.umpabackend.domain.SocialUser;
import promiseofblood.umpabackend.domain.User;
import promiseofblood.umpabackend.repository.Oauth2ProviderRepository;
import promiseofblood.umpabackend.repository.SocialUserRepository;
import promiseofblood.umpabackend.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final RestTemplate restTemplate;
  private final UserRepository userRepository;
  private final SocialUserRepository socialUserRepository;
  private final Oauth2ProviderRepository oauth2ProviderRepository;

  @Transactional
  public UserDto register(Oauth2RegisterRequest oauth2RegisterRequest) {
    Oauth2Provider naver = oauth2ProviderRepository.getByName("NAVER");

    // 네이버 accessToken 으로 Me api 호출
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oauth2RegisterRequest.getAccessToken());
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<NaverProfileResponse> response = restTemplate.exchange(
            naver.getProfileUri(),
            HttpMethod.GET,
            request,
            NaverProfileResponse.class
    );
    NaverProfileResponse naverProfileApiResponseDto = response.getBody();

    // User 객체 생성
    User user = User.builder()
            .name(oauth2RegisterRequest.getName())
            .profileImageUrl(naverProfileApiResponseDto.getResponse().getProfileImage())
            .build();
    userRepository.save(user);

    // SocialUser 객체 생성
    SocialUser socialUser = SocialUser.builder()
            .socialId(naverProfileApiResponseDto.getResponse().getId())
            .accessToken(oauth2RegisterRequest.getAccessToken())
            .refreshToken(oauth2RegisterRequest.getRefreshToken())
            .user(user)
            .oauth2Provider(oauth2ProviderRepository.getByName("NAVER"))
            .build();
    socialUserRepository.save(socialUser);

    return UserDto.of(user);
  }

  public NaverTokenResponse getAccessToken(String code) {
    Oauth2Provider naver = oauth2ProviderRepository.getByName("NAVER");

    String grantType = "authorization_code";
    String clientId = naver.getClientId();
    String clientSecret = naver.getClientSecret();
    String BaseUrl = naver.getTokenUri();

    NaverTokenResponse response = restTemplate.getForObject(
            BaseUrl + "?grant_type=" + grantType +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&code=" + code,
            NaverTokenResponse.class
    );
    log.info("response: {}", response);
    return response;
  }

  public String getLoginUrl() {
    Oauth2Provider naver = oauth2ProviderRepository.getByName("NAVER");

    String clientId = naver.getClientId();
    String redirectUri = naver.getRedirectUris().get(0);
    String baseUrl = naver.getLoginUrl();

    return baseUrl +
            "?response_type=code" +
            "&client_id=" + clientId +
            "&redirect_uri=" + redirectUri;
  }

}
