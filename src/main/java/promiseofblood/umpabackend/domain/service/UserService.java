package promiseofblood.umpabackend.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.core.exception.RegistrationException;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.LoginDto.LoginCompleteResponse;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;
import promiseofblood.umpabackend.dto.request.StudentProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.response.IsUsernameAvailableResponse;
import promiseofblood.umpabackend.repository.UserRepository;


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
    LoginDto.LoginIdPasswordRegisterRequest loginIdPasswordRegisterRequest) {

    if (this.isLoginIdAvailable(loginIdPasswordRegisterRequest.getLoginId())) {
      throw new RegistrationException("이미 사용 중인 로그인ID 입니다.");
    }

    User user = User.register(
      loginIdPasswordRegisterRequest.getLoginId(),
      loginIdPasswordRegisterRequest.getGender(),
      UserStatus.PENDING,
      Role.USER,
      loginIdPasswordRegisterRequest.getUsername(),
      loginIdPasswordRegisterRequest.getProfileType(),
      this.uploadProfileImage(
        loginIdPasswordRegisterRequest.getLoginId(),
        loginIdPasswordRegisterRequest.getProfileImage()
      )
    );
    user = userRepository.save(user);

    return LoginCompleteResponse.of(
      UserDto.ProfileResponse.from(user),
      LoginDto.JwtPairResponse.of(
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId())
      )
    );
  }

  /**
   * 사용자 목록을 조회합니다.
   *
   * @return 사용자 목록
   */
  public List<UserDto.ProfileResponse> getUsers() {

    return userRepository.findAll()
      .stream()
      .map(UserDto.ProfileResponse::from)
      .toList();
  }

  public UserDto.ProfileResponse getUserByLoginId(String loginId) {

    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    return UserDto.ProfileResponse.from(user);
  }

  public UserDto.ProfileResponse patchDefaultProfile(
    String loginId, DefaultProfilePatchRequest defaultProfilePatchRequest) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (defaultProfilePatchRequest.getUsername() != null) {
      user.patchUsername(defaultProfilePatchRequest.getUsername());
    }
    if (defaultProfilePatchRequest.getGender() != null) {
      user.patchGender(defaultProfilePatchRequest.getGender());
    }
    if (defaultProfilePatchRequest.getProfileImage() != null) {
      String storedFilePath = this.uploadProfileImage(
        user.getLoginId(),
        defaultProfilePatchRequest.getProfileImage()
      );
      user.patchProfileImageUrl(storedFilePath);
    }
    if (defaultProfilePatchRequest.getProfileType() != null) {
      user.patchProfileType(defaultProfilePatchRequest.getProfileType());
    }
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }

  @Transactional
  public UserDto.ProfileResponse patchTeacherProfile(
    String loginId, TeacherProfileRequest teacherProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    TeacherProfile teacherProfile = user.getTeacherProfile();

    if (teacherProfile == null) {
      teacherProfile = TeacherProfile.from(teacherProfileRequest);
    } else {
      teacherProfile.update(teacherProfileRequest);
    }

    user.patchTeacherProfile(teacherProfile);
    user = userRepository.save(user);

    return UserDto.ProfileResponse.from(user);
  }

  @Transactional
  public UserDto.ProfileResponse patchStudentProfile(
    String loginId, StudentProfileRequest studentProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    StudentProfile studentProfile = user.getStudentProfile();

    if (studentProfile == null) {
      studentProfile = StudentProfile.from(studentProfileRequest);
    } else {
      studentProfile.update(studentProfileRequest);
    }

    user.patchStudentProfile(studentProfile);
    userRepository.save(user);

    return UserDto.ProfileResponse.from(user);
  }

  public LoginCompleteResponse loginIdPasswordJwtLogin(String loginId, String password) {

    Optional<User> optionalUser = userRepository.findByLoginId(loginId);

    boolean userExists = optionalUser.isPresent();
    boolean isPasswordCorrect = optionalUser
      .map(user -> passwordEncoder.matches(password, user.getPassword()))
      .orElse(false);

    if (!userExists || !isPasswordCorrect) {
      throw new UnauthorizedException("사용자를 찾을 수 없습니다. 또는 비밀번호가 일치하지 않습니다.");
    }

    return LoginCompleteResponse.of(
      UserDto.ProfileResponse.from(optionalUser.get()),
      LoginDto.JwtPairResponse.of(
        jwtService.createAccessToken(optionalUser.get().getId(), optionalUser.get().getLoginId()),
        jwtService.createRefreshToken(optionalUser.get().getId(), optionalUser.get().getLoginId())
      )
    );
  }

  @Transactional
  public LoginDto.LoginCompleteResponse refreshToken(String refreshToken) {
    Long userId = jwtService.getUserIdFromToken(refreshToken);

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    LoginDto.JwtPairResponse jwtPairResponse = LoginDto.JwtPairResponse.of(
      jwtService.createAccessToken(user.getId(), user.getLoginId()),
      jwtService.createRefreshToken(user.getId(), user.getLoginId())
    );

    return LoginCompleteResponse.of(
      UserDto.ProfileResponse.from(user),
      jwtPairResponse);
  }

  public IsUsernameAvailableResponse isUsernameAvailable(String username) {
    if (!isUsernamePatternValid(username)) {
      return new IsUsernameAvailableResponse(username, false,
        "아이디는 한글, 영문, 숫자만 사용 가능하며 최대 8글자입니다.");
    }

    if (!isUsernameDuplicated(username)) {
      return new IsUsernameAvailableResponse(username, false, "이미 사용 중인 아이디입니다.");
    }

    return new IsUsernameAvailableResponse(username, true, "사용 가능한 아이디입니다.");
  }

  public boolean isUsernamePatternValid(String username) {
    String regex = "^[가-힣a-zA-Z0-9]{1,8}$";

    return username != null && username.matches(regex);
  }

  public boolean isUsernameDuplicated(String username) {

    return !userRepository.existsByUsername(username);
  }

  public String uploadProfileImage(String loginId, MultipartFile profileImage) {

    return storageService.store(
      profileImage,
      "users",
      loginId,
      "default-profile"
    );
  }

  private boolean isLoginIdAvailable(String loginId) {

    return userRepository.existsByLoginId(loginId);
  }


}
