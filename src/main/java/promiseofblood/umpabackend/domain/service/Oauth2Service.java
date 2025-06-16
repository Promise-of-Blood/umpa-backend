package promiseofblood.umpabackend.domain.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;
import promiseofblood.umpabackend.domain.entitiy.User;
import promiseofblood.umpabackend.domain.strategy.Oauth2Strategy;
import promiseofblood.umpabackend.domain.strategy.Oauth2StrategyFactory;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.JwtResponse;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.exception.InvalidJwtException;
import promiseofblood.umpabackend.exception.Oauth2UserAlreadyExists;
import promiseofblood.umpabackend.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final Map<String, Oauth2Strategy> oauth2Strategies;
  private final UserRepository userRepository;
  private final Oauth2UserRepository oauth2UserRepository;
  private final JwtService jwtService;


  @Transactional
  public RegisterCompleteResponse registerOauth2User(
    String providerName,
    Oauth2RegisterRequest oauth2RegisterRequest
  ) {
    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.create(providerName);

    // access token 으로 me api 호출 후 프로필 정보 받아오기
    Oauth2ProfileResponse oauth2ProfileResponse = oauth2Strategy.getOauth2UserProfileByIdToken(
      oauth2RegisterRequest.getExternalIdToken(),
      oauth2RegisterRequest.getExternalAccessToken(),
      oauth2Provider
    );

    // 중복 체크
    if (this.isOauth2UserAlreadyExists(providerName, oauth2ProfileResponse.getProviderUid())) {
      throw new Oauth2UserAlreadyExists("Oauth2 제공자: " + providerName + "(으)로 이미 가입된 사용자가 있습니다.");
    }

    Oauth2User newOauth2User = Oauth2User.builder()
      .providerName(oauth2Provider.getName())
      .providerUid(oauth2ProfileResponse.getProviderUid())
      .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
      .username(oauth2ProfileResponse.getUsername())
      .build();
    User newUser = User.builder()
      .oauth2User(newOauth2User)
      .build();
    User user = userRepository.save(newUser);

    // JWT 발급
    String accessToken = jwtService.createAccessToken(user.getId(), user.getUsername());
    String refreshToken = jwtService.createRefreshToken(user.getId());

    return RegisterCompleteResponse.builder()
      .user(UserDto.ofInitialUser(user))
      .jwtPair(JwtResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build())
      .build();
  }


  @Transactional
  public Oauth2ProfileResponse getOauth2Profile(String providerName, String code) {
    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.create(providerName);

    return oauth2Strategy.getOauth2UserProfile(code, provider);
  }


  public Map<String, String> generateAuthorizationUrls() {
    Map<String, Oauth2Provider> oauth2Providers = oauth2ProvidersConfig.getOauth2Providers();

    Map<String, String> urls = new java.util.HashMap<>(Map.of());
    for (Oauth2Provider provider : oauth2Providers.values()) {
      String providerName = provider.getName();

      Oauth2Strategy strategy = oauth2Strategies.get(
        "oauth2" +
          providerName.substring(0, 1).toUpperCase() +
          providerName.substring(1) +
          "Strategy"
      );

      if (strategy == null) {
        continue;
      }

      String authorizationUrl = strategy.getAuthorizationUrl(provider);
      urls.put(providerName, authorizationUrl);
    }

    return urls;
  }

  @Transactional
  public JwtResponse refreshToken(String refreshToken) {
    Long userId = jwtService.getUserIdFromToken(refreshToken);

    if (jwtService.isTokenExpired(refreshToken)) {
      throw new InvalidJwtException("리프레시 토큰이 만료되었습니다.");
    }

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    String newAccessToken = jwtService.createAccessToken(user.getId(), user.getUsername());
    String newRefreshToken = jwtService.createRefreshToken(user.getId());

    return JwtResponse.builder()
      .accessToken(newAccessToken)
      .refreshToken(newRefreshToken)
      .build();
  }

  public boolean isOauth2UserAlreadyExists(String providerName, String providerUid) {

    return oauth2UserRepository.existsByProviderUidAndProviderName(providerUid, providerName);
  }

}
