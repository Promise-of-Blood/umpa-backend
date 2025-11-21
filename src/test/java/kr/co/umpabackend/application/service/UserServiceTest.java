package kr.co.umpabackend.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.domain.vo.Gender;
import kr.co.umpabackend.domain.vo.ProfileType;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.UserStatus;
import kr.co.umpabackend.domain.vo.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private JwtService jwtService;
  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserService userService;

  @Test
  @DisplayName("isUsernameDuplicated 메서드는 닉네임이 중복되었을 경우 false를 반환한다.")
  void isUsernameDuplicated_UsernameExists_ReturnsTrue() {
    Username username = new Username("existing");
    when(userRepository.existsByUsername(username)).thenReturn(true);

    boolean isAvailable = userService.isUsernameDuplicated(username);

    assertFalse(isAvailable, "Username은 이미 존재하므로 사용 불가능해야 합니다.");
  }

  @Test
  @DisplayName("isUsernameDuplicated 메서드는 닉네임이 중복되지 않았을 경우 true를 반환한다.")
  void isUsernameDuplicated_UsernameDoesNotExist_ReturnsFalse() {
    Username username = new Username("new");
    when(userRepository.existsByUsername(username)).thenReturn(false);

    boolean isAvailable = userService.isUsernameDuplicated(username);

    assertTrue(isAvailable, "Username은 존재하지 않으므로 사용 가능해야 합니다.");
  }

  @Test
  @DisplayName("loginIdPasswordJwtLogin 메서드는 존재하지 않는 로그인ID로 로그인 시도 시 UnauthorizedException을 던진다.")
  void loginIdPasswordJwtLogin_NonExistentLoginId_ThrowsUnauthorizedException() {
    String nonExistentLoginId = "nonexistent";
    String password = "anyPassword";

    when(userRepository.findByLoginId(nonExistentLoginId)).thenReturn(Optional.empty());

    assertThrows(
        UnauthorizedException.class,
        () -> userService.loginIdPasswordJwtLogin(nonExistentLoginId, password),
        "존재하지 않는 로그인ID로 로그인 시도 시 UnauthorizedException이 발생해야 합니다.");
  }

  @Test
  @DisplayName(
      "loginIdPasswordJwtLogin 메서드는 알맞는 로그인ID 와 비밀번호로 로그인 시, user 프로필 정보와 함께 jwtPair를 반환한다.")
  void loginIdPasswordJwtLogin_ValidCredentials_ReturnsLoginCompleteResponse() {
    String loginId = "validUser";
    String rawPassword = "plainPassword";
    String encodedPassword = "encodedPassword";

    User user =
        User.register(
            loginId,
            encodedPassword,
            Gender.MALE,
            UserStatus.ACTIVE,
            Role.USER,
            "nickname",
            ProfileType.STUDENT,
            "http://example.com/profile.png");

    when(userRepository.findByLoginId(loginId)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
    when(jwtService.createAccessToken(user.getId(), user.getLoginId())).thenReturn("access-token");
    when(jwtService.createRefreshToken(user.getId(), user.getLoginId()))
        .thenReturn("refresh-token");

    var response = userService.loginIdPasswordJwtLogin(loginId, rawPassword);

    assertEquals(user.getLoginId(), response.getUser().getLoginId());
    assertEquals("access-token", response.getJwtPair().getAccessToken());
    assertEquals("refresh-token", response.getJwtPair().getRefreshToken());
  }
}
