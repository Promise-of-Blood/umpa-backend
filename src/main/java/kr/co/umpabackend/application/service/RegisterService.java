package kr.co.umpabackend.application.service;

import kr.co.umpabackend.application.exception.RegistrationException;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.UserStatus;
import kr.co.umpabackend.web.schema.request.RegisterByLoginIdPasswordRequest;
import kr.co.umpabackend.web.schema.response.LoginCompleteResponse;
import kr.co.umpabackend.web.schema.response.RetrieveFullProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {

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
        RetrieveFullProfileResponse.from(user, null),
        jwtService.createAccessToken(user.getId(), user.getLoginId()),
        jwtService.createRefreshToken(user.getId(), user.getLoginId()));
  }

  public String uploadProfileImage(String loginId, MultipartFile profileImage) {

    return storageService.store(profileImage, "users", loginId, "default-profile");
  }

  private boolean isLoginIdAvailable(String loginId) {

    return userRepository.existsByLoginId(loginId);
  }
}
