package promiseofblood.umpabackend.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;
import promiseofblood.umpabackend.domain.entitiy.TeacherCareer;
import promiseofblood.umpabackend.domain.entitiy.TeacherLink;
import promiseofblood.umpabackend.domain.entitiy.TeacherProfile;
import promiseofblood.umpabackend.domain.entitiy.User;
import promiseofblood.umpabackend.domain.strategy.Oauth2Strategy;
import promiseofblood.umpabackend.domain.strategy.Oauth2StrategyFactory;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2TeacherRegisterRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest.TeacherCareerRequest;
import promiseofblood.umpabackend.dto.response.JwtResponse;
import promiseofblood.umpabackend.dto.Oauth2UserDto;
import promiseofblood.umpabackend.repository.Oauth2UserRepository;
import promiseofblood.umpabackend.repository.TeacherCareerRepository;
import promiseofblood.umpabackend.repository.UserRepository;
import promiseofblood.umpabackend.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class Oauth2LoginService {

  private final Oauth2StrategyFactory oauth2StrategyFactory;
  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final Map<String, Oauth2Strategy> oauth2Strategies;
  private final Oauth2UserRepository oauth2UserRepository;
  private final TeacherCareerRepository teacherCareerRepository;
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;


  @Transactional
  public Map<String, Object> oauth2Register(
    String providerName,
    Oauth2TeacherRegisterRequest oauth2TeacherRegisterRequest) {

    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.create(providerName);

    // 1. access token 으로 me api 호출 후 프로필 정보 받아오기
    String externalAccessToken = oauth2TeacherRegisterRequest.getExternalAccessToken();
    Oauth2ProfileResponse oauth2ProfileResponse =
      oauth2Strategy.getUserInfo(externalAccessToken, provider);

    // 2. CareerRequest 를 TeacherCareer 로 변환
    List<TeacherCareer> teacherCareers = new ArrayList<>();
    for (TeacherCareerRequest career : oauth2TeacherRegisterRequest.getTeacherProfileRequest()
      .getCareers()) {
      TeacherCareer teacherCareer = TeacherCareer.builder()
        .isRepresentative(career.isRepresentative())
        .title(career.getTitle())
        .start(career.getStartDate())
        .end(career.getEndDate())
        .build();
      teacherCareers.add(teacherCareer);
    }

    List<TeacherLink> teacherLinks = new ArrayList<>();
    for (String link : oauth2TeacherRegisterRequest.getTeacherProfileRequest().getLinks()) {
      TeacherLink teacherLink = TeacherLink.builder()
        .link(link)
        .build();
      teacherLinks.add(teacherLink);
    }

    // 3. TeacherCareer 를 TeacherProfile 로 변환
    TeacherProfile teacherProfile = TeacherProfile.builder()
      .lessonRegion(
        Region.valueOf(oauth2TeacherRegisterRequest.getTeacherProfileRequest().getLessonRegion()))
      .careers(teacherCareers)
      .links(teacherLinks)
      .build();

    // 4. 받아온 프로필 정보로 Oauth2User 생성
    Oauth2User newOauth2User = Oauth2User.builder()
      .providerName(provider.getName())
      .providerUid(oauth2ProfileResponse.getProviderUid())
      .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
      .username(oauth2TeacherRegisterRequest.getUsername())
      .build();

    // 5. User 객체 생성
    User newUser = User.builder()
      .username(oauth2TeacherRegisterRequest.getUsername())
      .gender(Gender.valueOf(oauth2TeacherRegisterRequest.getGender()))
      .profileImageUrl(oauth2ProfileResponse.getProfileImageUrl())
      .major(Major.valueOf(oauth2TeacherRegisterRequest.getMajor()))
      .oauth2User(newOauth2User)
      .teacherProfile(teacherProfile)
      .build();
    User user = userRepository.save(newUser);

    // 6. JWT 발급
    String accessToken = jwtUtils.createAccessToken(user.getId(), user.getUsername());
    String refreshToken = jwtUtils.createRefreshToken(user.getId());
    JwtResponse jwtResponse = JwtResponse.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();

    UserDto userDto = UserDto.of(user);

    return Map.of(
      "user", userDto,
      "jwt", jwtResponse
    );
  }

  @Transactional
  public Oauth2ProfileResponse getOauth2Profile(String providerName, String code) {
    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);
    Oauth2Strategy oauth2Strategy = oauth2StrategyFactory.create(providerName);

    // 1. provider 로부터 code 로 액세스 토큰 받아오기
    String externalAccessToken = oauth2Strategy.getAccessToken(code, provider);

    // 2. access token 으로 me api 호출 후 프로필 정보 받아오기
    Oauth2ProfileResponse oauth2ProfileResponse =
      oauth2Strategy.getUserInfo(externalAccessToken, provider);

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

  @Transactional
  public JwtResponse refreshToken(String refreshToken) {
    // 1. Validate refresh token
    if (refreshToken == null) {
      throw new RuntimeException("Refresh token is missing");
    }

    // 2. Extract user ID from refresh token
    Long userId;
    userId = jwtUtils.getUserIdFromToken(refreshToken);

    // 3. Check if token is expired
    if (jwtUtils.isTokenExpired(refreshToken)) {
      throw new RuntimeException("Refresh token has expired");
    }

    // 4. Get user from database
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    // 5. Generate new tokens
    String newAccessToken = jwtUtils.createAccessToken(user.getId(), user.getUsername());
    String newRefreshToken = jwtUtils.createRefreshToken(user.getId());

    // 6. Return new tokens
    return JwtResponse.builder()
      .accessToken(newAccessToken)
      .refreshToken(newRefreshToken)
      .build();
  }

}
