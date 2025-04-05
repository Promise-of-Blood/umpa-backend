package promiseofblood.umpabackend.domain.service;


import jakarta.transaction.Transactional;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.strategy.Oauth2RegisterStrategy;
import promiseofblood.umpabackend.dto.external.Oauth2UserInfoResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;
import promiseofblood.umpabackend.dto.response.Oauth2LoginUrlResponse;
import promiseofblood.umpabackend.dto.response.Oauth2RegisterResponse;
import promiseofblood.umpabackend.dto.response.Oauth2TokenResponse;
import promiseofblood.umpabackend.exception.NotSupportedOauth2ProviderException;
import promiseofblood.umpabackend.repository.*;
import promiseofblood.umpabackend.utils.JwtUtils;


@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2Service {

  private final Map<String, Oauth2RegisterStrategy> oauth2RegisterStrategies;
  private final UserTypeRepository userTypeRepository;
  private final CollegeRepository collegeRepository;
  private final UserRepository userRepository;
  private final MajorRepository majorRepository;
  private final Oauth2ProviderRepository oauth2ProviderRepository;

  @Transactional
  public Oauth2RegisterResponse register(Oauth2RegisterRequest oauth2RegisterRequest) {

    Oauth2Provider oauth2Provider = this.getOauth2ProviderByName(
      oauth2RegisterRequest.getOauth2ProviderName());
    Oauth2RegisterStrategy oauth2RegisterStrategy = getOauth2RegisterStrategy(oauth2Provider);

    Oauth2UserInfoResponse newOauth2UserDto = oauth2RegisterStrategy.getOauth2UserInfo(
      oauth2Provider, oauth2RegisterRequest
    );

    User user = User.builder()
      .name(oauth2RegisterRequest.getName())
      .profileImageUrl(null)
      .userType(userTypeRepository.getByName(oauth2RegisterRequest.getUserType()))
      .major(majorRepository.getByName(oauth2RegisterRequest.getMajor()))
      .wantedColleges(
        oauth2RegisterRequest.getWantedColleges().stream()
          .map(collegeRepository::getByName)
          .toList()
      )
      .oauth2User(
        Oauth2User.builder()
          .socialId(newOauth2UserDto.getSocialId())
          .accessToken(oauth2RegisterRequest.getAccessToken())
          .refreshToken(oauth2RegisterRequest.getRefreshToken())
          .oauth2Provider(oauth2Provider)
          .build()
      )
      .build();
    userRepository.save(user);

    String accessToken = JwtUtils.createAccessToken(user.getId(), user.getName());
    return Oauth2RegisterResponse.of(user, accessToken);
  }

  public Oauth2TokenResponse getOauth2AccessToken(String oauth2ProviderName, String code) {
    Oauth2Provider oauth2Provider = getOauth2ProviderByName(oauth2ProviderName);
    Oauth2RegisterStrategy oauth2RegisterStrategy = getOauth2RegisterStrategy(oauth2Provider);

    return oauth2RegisterStrategy.getOauth2Token(oauth2Provider, code);
  }

  public Oauth2LoginUrlResponse getLoginUrl(String oauth2ProviderName) {
    Oauth2Provider oauth2Provider = getOauth2ProviderByName(oauth2ProviderName);
    Oauth2RegisterStrategy oauth2RegisterStrategy = getOauth2RegisterStrategy(oauth2Provider);

    return Oauth2LoginUrlResponse.builder()
      .url(oauth2RegisterStrategy.getLoginUrl(oauth2Provider))
      .build();
  }

  private Oauth2RegisterStrategy getOauth2RegisterStrategy(Oauth2Provider oauth2Provider) {
    Oauth2RegisterStrategy oauth2RegisterStrategy = oauth2RegisterStrategies.get(
      oauth2Provider.getName().toLowerCase() + "RegisterStrategy");

    if (oauth2RegisterStrategy == null) {
      throw new NotSupportedOauth2ProviderException("구현되지 않은 소셜 로그인 제공자입니다.");
    }

    return oauth2RegisterStrategy;
  }

  private Oauth2Provider getOauth2ProviderByName(String oauth2ProviderName) {
    Oauth2Provider oauth2Provider = oauth2ProviderRepository.findByName(
      oauth2ProviderName.toUpperCase());

    if (oauth2Provider == null) {
      throw new NotSupportedOauth2ProviderException("지원되지 않는 소셜 로그인 제공자입니다.");
    }

    return oauth2Provider;
  }

}
