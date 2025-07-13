package promiseofblood.umpabackend.domain.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.core.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.core.exception.NotSupportedOauth2ProviderException;
import promiseofblood.umpabackend.core.exception.Oauth2UserAlreadyExists;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.Oauth2User;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.strategy.Oauth2Strategy;
import promiseofblood.umpabackend.domain.strategy.Oauth2StrategyFactory;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final UserRepository userRepository;
  private final Oauth2UserRepository oauth2UserRepository;
  private final JwtService jwtService;


  @Transactional
  public RegisterCompleteResponse registerOauth2User(
    String providerName,
    Oauth2RegisterRequest oauth2RegisterRequest
  ) {
    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

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
      .loginId(providerName + System.currentTimeMillis())
      .status(Status.ACTIVE)
      .role(Role.USER)
      .username("임의의사용자" + System.currentTimeMillis())
      .oauth2User(newOauth2User)
      .build();
    User user = userRepository.save(newUser);

    return RegisterCompleteResponse.builder()
      .user(UserDto.of(user))
      .jwtPair(JwtPairDto.builder()
        .accessToken(jwtService.createAccessToken(user.getId(), user.getLoginId()))
        .refreshToken(jwtService.createRefreshToken(user.getId(), user.getLoginId()))
        .build())
      .build();
  }


  @Transactional
  public Oauth2ProfileResponse getOauth2Profile(String providerName, String code) {
    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    return oauth2Strategy.getOauth2UserProfile(oauth2Provider, code);
  }

  public JwtPairDto generateOauth2Jwt(
    String providerName,
    String externalIdToken,
    String externalAccessToken
  ) {

    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    User user = userRepository.findByOauth2User_ProviderNameAndOauth2User_ProviderUid(
        providerName,
        oauth2Strategy.getOauth2UserProfile(oauth2Provider, externalAccessToken, externalIdToken)
          .getProviderUid())
      .orElseThrow(() -> new UnauthorizedException("해당 Oauth2 사용자 정보가 존재하지 않습니다."));

    return JwtPairDto.builder()
      .accessToken(jwtService.createAccessToken(user.getId(), user.getLoginId()))
      .refreshToken(jwtService.createRefreshToken(user.getId(), user.getLoginId()))
      .build();
  }


  public Map<String, Oauth2ProviderDto> generateAuthorizationUrls() {
    Map<String, Oauth2ProviderDto> oauth2ProviderNameToInfo = new HashMap<>();

    for (Oauth2Provider oauth2Provider : oauth2ProvidersConfig.getProviders()) {
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


  public boolean isOauth2UserAlreadyExists(String providerName, String providerUid) {

    return oauth2UserRepository.existsByProviderUidAndProviderName(providerUid, providerName);
  }

}
