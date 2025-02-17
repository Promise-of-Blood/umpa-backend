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
import promiseofblood.umpabackend.domain.Oauth2Provider;
import promiseofblood.umpabackend.domain.SocialUser;
import promiseofblood.umpabackend.domain.User;
import promiseofblood.umpabackend.dto.response.Oauth2LoginUrlResponse;
import promiseofblood.umpabackend.dto.response.Oauth2RegisterResponse;
import promiseofblood.umpabackend.repository.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final RestTemplate restTemplate;
  private final UserTypeRepository userTypeRepository;
  private final CollegeRepository collegeRepository;
  private final UserRepository userRepository;
  private final MajorRepository majorRepository;
  private final Oauth2ProviderRepository oauth2ProviderRepository;

  @Transactional
  public Oauth2RegisterResponse register(Oauth2RegisterRequest oauth2RegisterRequest) {
    Oauth2Provider oauth2Provider = oauth2ProviderRepository.getByName(oauth2RegisterRequest.getOauth2Provider());

    // 네이버 accessToken 으로 Me api 호출
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oauth2RegisterRequest.getAccessToken());
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<NaverProfileResponse> response = restTemplate.exchange(
            oauth2Provider.getProfileUri(),
            HttpMethod.GET,
            request,
            NaverProfileResponse.class
    );
    NaverProfileResponse naverProfileApiResponseDto = response.getBody();

    // User 객체 생성
    User user = User.builder()
            .name(oauth2RegisterRequest.getName())
            .profileImageUrl(naverProfileApiResponseDto.getResponse().getProfileImage())
            .userType(userTypeRepository.getByName(oauth2RegisterRequest.getUserType()))
            .major(majorRepository.getByName(oauth2RegisterRequest.getMajor()))
            .wantedColleges(
                    oauth2RegisterRequest.getWantedColleges().stream()
                            .map(collegeRepository::getByName)
                            .toList()
            )
            .socialUser(
                    SocialUser.builder()
                            .socialId(naverProfileApiResponseDto.getResponse().getId())
                            .accessToken(oauth2RegisterRequest.getAccessToken())
                            .refreshToken(oauth2RegisterRequest.getRefreshToken())
                            .oauth2Provider(oauth2Provider)
                            .build()
            )
            .build();
    userRepository.save(user);

    return Oauth2RegisterResponse.of(user);
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

  public Oauth2LoginUrlResponse getLoginUrl() {
    Oauth2Provider naver = oauth2ProviderRepository.getByName("NAVER");

    String clientId = naver.getClientId();
    String redirectUri = naver.getRedirectUris().get(0);
    String baseUrl = naver.getLoginUrl();

    return Oauth2LoginUrlResponse.builder()
            .url(baseUrl +
                    "?response_type=code" +
                    "&client_id=" + clientId +
                    "&redirect_uri=" + redirectUri)
            .build();
  }

}
