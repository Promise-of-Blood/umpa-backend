package promiseofblood.umpabackend.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.application.exception.RegistrationException;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.domain.vo.Username;
import promiseofblood.umpabackend.web.schema.request.RegisterByLoginIdPasswordRequest;
import promiseofblood.umpabackend.web.schema.request.RegisterByLoginIdPasswordWithRoleRequest;
import promiseofblood.umpabackend.web.schema.response.CheckIsUsernameAvailableResponse;
import promiseofblood.umpabackend.web.schema.response.LoginCompleteResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final StorageService storageService;

  @Transactional
  public LoginCompleteResponse registerUser(
      RegisterByLoginIdPasswordRequest loginIdPasswordRegisterRequest) {

    if (this.isLoginIdAvailable(loginIdPasswordRegisterRequest.getLoginId())) {
      throw new RegistrationException("이미 사용 중인 로그인ID 입니다.");
    }

    // TODO 이 더러운 코드를 해결하기
    String storedFilePath = null;
    if (loginIdPasswordRegisterRequest.getProfileImage() != null) {
      storedFilePath =
          this.uploadProfileImage(
              loginIdPasswordRegisterRequest.getLoginId(),
              loginIdPasswordRegisterRequest.getProfileImage());
    }

    User user =
        User.register(
            loginIdPasswordRegisterRequest.getLoginId(),
            passwordEncoder.encode(loginIdPasswordRegisterRequest.getPassword()),
            loginIdPasswordRegisterRequest.getGender(),
            UserStatus.PENDING,
            Role.USER,
            loginIdPasswordRegisterRequest.getUsername(),
            loginIdPasswordRegisterRequest.getProfileType(),
            storedFilePath);
    user = userRepository.save(user);

    return LoginCompleteResponse.of(
        RetrieveFullProfileResponse.from(user),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  @Transactional
  public RetrieveFullProfileResponse registerAdmin(
      RegisterByLoginIdPasswordWithRoleRequest adminRegisterRequest) {

    if (this.isLoginIdAvailable(adminRegisterRequest.loginId())) {
      throw new RegistrationException("이미 사용 중인 로그인ID입니다.");
    }

    if (!this.isUsernameDuplicated(new Username(adminRegisterRequest.username()))) {
      throw new RegistrationException("이미 사용 중인 닉네임입니다.");
    }

    String storedFilePath = null;
    if (adminRegisterRequest.profileImage() != null) {
      storedFilePath =
          this.uploadProfileImage(
              adminRegisterRequest.loginId(), adminRegisterRequest.profileImage());
    }

    User user =
        User.register(
            adminRegisterRequest.loginId(),
            passwordEncoder.encode(adminRegisterRequest.password()),
            adminRegisterRequest.gender(),
            UserStatus.ACTIVE,
            adminRegisterRequest.role(),
            adminRegisterRequest.username(),
            adminRegisterRequest.profileType(),
            storedFilePath);

    user = userRepository.save(user);

    return RetrieveFullProfileResponse.from(user);
  }

  /**
   * 사용자 목록을 조회합니다.
   *
   * @return 사용자 목록
   */
  @Transactional(readOnly = true)
  public List<RetrieveFullProfileResponse> getUsers() {

    return userRepository.findAll().stream().map(RetrieveFullProfileResponse::from).toList();
  }

  @Transactional(readOnly = true)
  public RetrieveFullProfileResponse getUserByLoginId(String loginId) {

    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    return RetrieveFullProfileResponse.from(user);
  }

  @Transactional(readOnly = true)
  public LoginCompleteResponse loginIdPasswordJwtLogin(String loginId, String password) {

    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new UnauthorizedException("사용자를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
    }

    return LoginCompleteResponse.of(
        RetrieveFullProfileResponse.from(user),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  @Transactional
  public LoginCompleteResponse refreshToken(String refreshToken) {
    jwtService.verifyJwt(refreshToken);

    if (!jwtService.getTypeFromToken(refreshToken).equals("refresh")) {
      throw new UnauthorizedException("유효하지 않은 토큰입니다.");
    }

    Long userId = jwtService.getUserIdFromToken(refreshToken);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    return LoginCompleteResponse.of(
        RetrieveFullProfileResponse.from(user),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  public CheckIsUsernameAvailableResponse isUsernameAvailable(String rawUsername) {
    if (!isUsernamePatternValid(rawUsername)) {
      return new CheckIsUsernameAvailableResponse(
          rawUsername, false, "아이디는 한글, 영문, 숫자만 사용 가능하며 최대 8글자입니다.");
    }

    Username username = new Username(rawUsername);

    if (!isUsernameDuplicated(username)) {
      return new CheckIsUsernameAvailableResponse(rawUsername, false, "이미 사용 중인 아이디입니다.");
    }

    return new CheckIsUsernameAvailableResponse(rawUsername, true, "사용 가능한 아이디입니다.");
  }

  public boolean isUsernamePatternValid(String username) {
    String regex = "^[가-힣a-zA-Z0-9]{1,8}$";

    return username != null && username.matches(regex);
  }

  public boolean isUsernameDuplicated(Username username) {

    return !userRepository.existsByUsername(username);
  }

  public String uploadProfileImage(String loginId, MultipartFile profileImage) {

    return storageService.store(profileImage, "users", loginId, "default-profile");
  }

  private boolean isLoginIdAvailable(String loginId) {

    return userRepository.existsByLoginId(loginId);
  }
}
