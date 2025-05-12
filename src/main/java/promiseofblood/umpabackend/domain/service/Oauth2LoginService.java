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
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Oauth2LoginService {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final Map<String, Oauth2Strategy> oauth2Strategies;
  private final Oauth2UserRepository oauth2UserRepository;
  private final UserRepository userRepository;

  @Transactional
  public Oauth2ProfileResponse getOauth2Profile(String providerName, String code) {
    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.create(providerName);

    // 1. provider 로부터 code 로 액세스 토큰 받아오기
    String externalAccessToken = oauth2Strategy.getAccessToken(code, provider);

    // 2. access token 으로 me api 호출 후 프로필 정보 받아오기
    Oauth2ProfileResponse oauth2ProfileResponse =
      oauth2Strategy.getUserInfo(externalAccessToken, provider);

    // 3. 받아온 프로필 정보로 Oauth2User 생성
    Oauth2User oauth2User = Oauth2User.builder()
      .providerUid(oauth2ProfileResponse.getProviderUid())
      .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
      .username(oauth2ProfileResponse.getUsername())
      .build();

    // 4. Oauth2User 를 DB 에 저장
    oauth2UserRepository.save(oauth2User);

    // 5. User 객체 생성
    User newUser = User.builder()
      .username(oauth2ProfileResponse.getUsername())
      .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
      .oauth2UserId(oauth2User.getId())
      .build();

    // 6. User 객체를 DB 에 저장
    userRepository.save(newUser);

    return oauth2ProfileResponse;

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

}
