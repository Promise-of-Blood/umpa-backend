package promiseofblood.umpabackend.domain.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.vo.Oauth2Providers;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;
import promiseofblood.umpabackend.domain.entitiy.User;
import promiseofblood.umpabackend.domain.strategy.Oauth2Strategy;
import promiseofblood.umpabackend.domain.strategy.Oauth2StrategyFactory;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.JwtResponse;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.exception.InvalidJwtException;
import promiseofblood.umpabackend.exception.NotSupportedOauth2ProviderException;
import promiseofblood.umpabackend.exception.Oauth2UserAlreadyExists;
import promiseofblood.umpabackend.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2Providers oauth2Providers;
  private final UserRepository userRepository;
  private final Oauth2UserRepository oauth2UserRepository;
  private final JwtService jwtService;


  @Transactional
  public RegisterCompleteResponse registerOauth2User(
    String providerName,
    Oauth2RegisterRequest oauth2RegisterRequest
  ) {
    Oauth2Provider oauth2Provider = oauth2Providers.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    // access token 으로 me api 호출 후 프로필 정보 받아오기
    Oauth2ProfileResponse oauth2ProfileResponse = oauth2Strategy.getOauth2UserProfile(
      oauth2Provider,
      oauth2RegisterRequest.getExternalAccessToken(),
      oauth2RegisterRequest.getExternalIdToken());

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
    Oauth2Provider oauth2Provider = oauth2Providers.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    return oauth2Strategy.getOauth2UserProfile(oauth2Provider, code);
  }


  public Map<String, Oauth2ProviderDto> generateAuthorizationUrls() {
    Map<String, Oauth2ProviderDto> oauth2ProviderNameToInfo = new HashMap<>();

    for (Oauth2Provider oauth2Provider : oauth2Providers.getProviders()) {
      try {
        Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(oauth2Provider.getName());
        oauth2ProviderNameToInfo.put(
          oauth2Provider.getName(),
          Oauth2ProviderDto.builder()
            .name(oauth2Provider.getName())
            .clientId(oauth2Provider.getClientId())
            .loginUrl(oauth2Strategy.getAuthorizationUrl(oauth2Provider))
            .tokenUri(oauth2Provider.getTokenUri())
            .profileUri(oauth2Provider.getProfileUri())
            .redirectUri(oauth2Provider.getRedirectUri())
            .build());
      } catch (NotSupportedOauth2ProviderException e) {
        oauth2ProviderNameToInfo.put(oauth2Provider.getName(), null);
      }
    }

    return oauth2ProviderNameToInfo;
  }

  @Transactional
  public JwtResponse refreshToken(String refreshToken) {
    Long userId = jwtService.getUserIdFromToken(refreshToken);

    if (jwtService.isTokenExpired(refreshToken)) {
      throw new InvalidJwtException("리프레시 토큰이 만료되었습니다.");
    }

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

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
