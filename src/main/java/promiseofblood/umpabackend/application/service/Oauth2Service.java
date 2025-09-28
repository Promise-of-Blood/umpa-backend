package promiseofblood.umpabackend.application.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.NotSupportedOauth2ProviderException;
import promiseofblood.umpabackend.application.exception.Oauth2UserAlreadyExists;
import promiseofblood.umpabackend.application.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.Oauth2User;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.infrastructure.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.infrastructure.oauth.Oauth2Strategy;
import promiseofblood.umpabackend.infrastructure.oauth.Oauth2StrategyFactory;
import promiseofblood.umpabackend.infrastructure.oauth.dto.Oauth2ProfileResponse;
import promiseofblood.umpabackend.web.schema.request.RegisterByOauth2Request;
import promiseofblood.umpabackend.web.schema.response.CheckIsOauth2RegisterAvailableResponse;
import promiseofblood.umpabackend.web.schema.response.LoginCompleteResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final UserRepository userRepository;
  private final UserService userService;
  private final Oauth2UserRepository oauth2UserRepository;
  private final JwtService jwtService;

  @Transactional
  public LoginCompleteResponse registerOauth2User(
      String providerName, RegisterByOauth2Request oauth2RegisterRequest) {
    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    Oauth2ProfileResponse oauth2ProfileResponse =
        oauth2Strategy.getOauth2UserProfile(
            oauth2Provider,
            oauth2RegisterRequest.getExternalAccessToken(),
            oauth2RegisterRequest.getExternalIdToken());

    // 중복 체크
    if (this.isOauth2UserAlreadyExists(providerName, oauth2ProfileResponse.getProviderUid())) {
      throw new Oauth2UserAlreadyExists("Oauth2 제공자: " + providerName + "(으)로 이미 가입된 사용자가 있습니다.");
    }

    Oauth2User newOauth2User =
        Oauth2User.builder()
            .providerName(oauth2Provider.getName())
            .providerUid(oauth2ProfileResponse.getProviderUid())
            .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
            .username(oauth2ProfileResponse.getUsername())
            .build();

    String loginId = oauth2ProfileResponse.getProviderUid();

    // TODO 이 더러운 코드를 해결하기
    String storedFilePath = null;
    if (oauth2RegisterRequest.getProfileImage() != null) {
      storedFilePath =
          userService.uploadProfileImage(loginId, oauth2RegisterRequest.getProfileImage());
    }

    User newUser =
        User.register(
            loginId,
            oauth2RegisterRequest.getGender(),
            UserStatus.PENDING,
            Role.USER,
            oauth2RegisterRequest.getUsername(),
            oauth2RegisterRequest.getProfileType(),
            storedFilePath,
            newOauth2User);

    User user = userRepository.save(newUser);

    return LoginCompleteResponse.of(
        RetrieveFullProfileResponse.from(user),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  @Transactional
  public Oauth2ProfileResponse getOauth2Profile(String providerName, String code) {
    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    return oauth2Strategy.getOauth2UserProfile(oauth2Provider, code);
  }

  @Transactional(readOnly = true)
  public LoginCompleteResponse generateOauth2Jwt(
      String providerName, String externalIdToken, String externalAccessToken) {

    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    User user =
        userRepository
            .findByOauth2User_ProviderNameAndOauth2User_ProviderUid(
                providerName,
                oauth2Strategy
                    .getOauth2UserProfile(oauth2Provider, externalAccessToken, externalIdToken)
                    .getProviderUid())
            .orElseThrow(() -> new UnauthorizedException("가입하지 않은 Oauth2 사용자입니다."));

    return LoginCompleteResponse.of(
        RetrieveFullProfileResponse.from(user),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  public CheckIsOauth2RegisterAvailableResponse isOauth2RegisterAvailable(
      String providerName, String idToken, String accessToken) {

    Oauth2Provider oauth2Provider = oauth2ProvidersConfig.get(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.getStrategy(providerName);

    Oauth2ProfileResponse oauth2ProfileResponse =
        oauth2Strategy.getOauth2UserProfile(oauth2Provider, accessToken, idToken);
    String providerUid = oauth2ProfileResponse.getProviderUid();

    Optional<User> user =
        userRepository.findByOauth2User_ProviderNameAndOauth2User_ProviderUid(
            providerName, providerUid);

    String message;
    if (user.isPresent()) {
      message = "이미 가입된 사용자입니다.";
    } else {
      message = "가입 가능한 사용자입니다.";
    }

    return new CheckIsOauth2RegisterAvailableResponse(providerName, user.isEmpty(), message);
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
