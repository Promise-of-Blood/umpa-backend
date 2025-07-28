package promiseofblood.umpabackend.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.core.exception.RegistrationException;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.ProfileType;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.LoginDto.LoginIdPasswordRequest;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;
import promiseofblood.umpabackend.dto.request.StudentProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.response.TeacherProfileResponse;
import promiseofblood.umpabackend.repository.UserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final StorageService storageService;

  public LoginDto.LoginCompleteResponse registerUser(
    LoginIdPasswordRequest loginIdPasswordRequest) {

    if (this.isLoginIdAvailable(loginIdPasswordRequest.getLoginId())) {
      throw new RegistrationException("이미 사용 중인 로그인ID 입니다.");
    }

    User user = User.register(
      loginIdPasswordRequest.getLoginId(),
      Status.ACTIVE,
      Role.USER,
      "임의의사용자" + System.currentTimeMillis(),
      ProfileType.STUDENT
    );
    user = userRepository.save(user);

    JwtPairDto jwtPairDto = JwtPairDto.builder()
      .accessToken(jwtService.createAccessToken(user.getId(), user.getLoginId()))
      .refreshToken(jwtService.createRefreshToken(user.getId(), user.getLoginId()))
      .build();

    return LoginDto.LoginCompleteResponse.of(
      UserDto.ProfileResponse.from(user),
      LoginDto.JwtPairResponse.of(jwtPairDto.getAccessToken(), jwtPairDto.getRefreshToken())
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
      String storedFilePath = storageService.store(
        defaultProfilePatchRequest.getProfileImage(),
        "users",
        user.getId().toString(),
        "default-profile"
      );
      user.patchProfileImageUrl(storedFilePath);
    }
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }

  @Transactional
  public TeacherProfileResponse patchTeacherProfile(
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

    return TeacherProfileResponse.from(user.getTeacherProfile());
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

  public LoginDto.LoginCompleteResponse loginIdPasswordJwtLogin(String loginId, String password) {

    Optional<User> optionalUser = userRepository.findByLoginId(loginId);

    boolean userExists = optionalUser.isPresent();
    boolean isPasswordCorrect = optionalUser
      .map(user -> passwordEncoder.matches(password, user.getPassword()))
      .orElse(false);

    if (!userExists || !isPasswordCorrect) {
      throw new UnauthorizedException("사용자를 찾을 수 없습니다. 또는 비밀번호가 일치하지 않습니다.");
    }

    return LoginDto.LoginCompleteResponse.of(
      UserDto.ProfileResponse.from(optionalUser.get()),
      LoginDto.JwtPairResponse.of(
        jwtService.createAccessToken(optionalUser.get().getId(), optionalUser.get().getLoginId()),
        jwtService.createRefreshToken(optionalUser.get().getId(), optionalUser.get().getLoginId())
      )
    );
  }

  @Transactional
  public JwtPairDto refreshToken(String refreshToken) {
    Long userId = jwtService.getUserIdFromToken(refreshToken);

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    String newAccessToken = jwtService.createAccessToken(user.getId(), user.getLoginId());
    String newRefreshToken = jwtService.createRefreshToken(user.getId(), user.getLoginId());

    return JwtPairDto.builder()
      .accessToken(newAccessToken)
      .refreshToken(newRefreshToken)
      .build();
  }

  public boolean isUsernameAvailable(String username) {

    return !userRepository.existsByUsername(username);
  }


  private boolean isLoginIdAvailable(String loginId) {

    return userRepository.existsByLoginId(loginId);
  }


}
