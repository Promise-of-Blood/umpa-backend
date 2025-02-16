package promiseofblood.umpabackend.user.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import promiseofblood.umpabackend.user.dto.RegisterRequestDto;
import promiseofblood.umpabackend.user.dto.user.oauth2.naver.NaverProfileApiResponseDto;
import promiseofblood.umpabackend.user.dto.user.oauth2.naver.NaverTokenApiResponseDto;
import promiseofblood.umpabackend.user.dto.user.UserDto;
import promiseofblood.umpabackend.user.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.user.entitiy.SocialUser;
import promiseofblood.umpabackend.user.entitiy.User;
import promiseofblood.umpabackend.user.repository.Oauth2ProviderRepository;
import promiseofblood.umpabackend.user.repository.SocialUserRepository;
import promiseofblood.umpabackend.user.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service {

  private final RestTemplate restTemplate;
  private final UserRepository userRepository;
  private final SocialUserRepository socialUserRepository;
  private final Oauth2ProviderRepository oauth2ProviderRepository;

  @Transactional
  public UserDto registerWithNaver(RegisterRequestDto registerRequestDto) {
    // 네이버 accessToken 으로 Me api 호출
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + registerRequestDto.getAccessToken());
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<NaverProfileApiResponseDto> response = restTemplate.exchange(
            "https://openapi.naver.com/v1/nid/me",
            HttpMethod.GET,
            request,
            NaverProfileApiResponseDto.class
    );
    NaverProfileApiResponseDto naverProfileApiResponseDto = response.getBody();

    // User 객체 생성
    User user = User.builder()
            .name(registerRequestDto.getName())
            .profileImageUrl(naverProfileApiResponseDto.getResponse().getProfileImage())
            .build();
    userRepository.save(user);

    // SocialUser 객체 생성
    SocialUser socialUser = SocialUser.builder()
            .socialId(naverProfileApiResponseDto.getResponse().getId())
            .accessToken(registerRequestDto.getAccessToken())
            .refreshToken(registerRequestDto.getRefreshToken())
            .user(user)
            .oauth2Provider(oauth2ProviderRepository.getByName("NAVER"))
            .build();
    socialUserRepository.save(socialUser);

    return UserDto.of(user);
  }

  public NaverTokenApiResponseDto getAccessToken(String code) {
    Oauth2Provider naver = oauth2ProviderRepository.getByName("NAVER");

    String grantType = "authorization_code";
    String clientId = naver.getClientId();
    String clientSecret = naver.getClientSecret();
    String BaseUrl = naver.getTokenUri();

    NaverTokenApiResponseDto response = restTemplate.getForObject(
            BaseUrl + "?grant_type=" + grantType +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&code=" + code,
            NaverTokenApiResponseDto.class
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
