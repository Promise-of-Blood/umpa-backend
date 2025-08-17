package promiseofblood.umpabackend.domain.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promiseofblood.umpabackend.dto.response.IsUsernameAvailableResponse;
import promiseofblood.umpabackend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("isUsernameAvailable 메서드는 닉네임이 중복되었을 경우 false를 반환한다.")
  void isUsernameAvailable_UsernameExists_ReturnsTrue() {
    String username = "existing-user";
    when(userRepository.existsByUsername(username)).thenReturn(true);

    boolean isAvailable = userService.isUsernameDuplicated(username);

    assertFalse(isAvailable, "Username은 이미 존재하므로 사용 불가능해야 합니다.");
  }

  @Test
  @DisplayName("isUsernameAvailable 메서드는 닉네임이 중복되지 않았을 경우 true를 반환한다.")
  void isUsernameAvailable_UsernameDoesNotExist_ReturnsFalse() {
    String username = "new-user";
    when(userRepository.existsByUsername(username)).thenReturn(false);

    boolean isAvailable = userService.isUsernameDuplicated(username);

    assertTrue(isAvailable, "Username은 존재하지 않으므로 사용 가능해야 합니다.");
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "홍길동abcabc",
    "ABCABCABC",
    "일이삼사오육칠팔구십",
    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
  })
  void 닉네임이_8글자_초과인경우_사용불가능(String name) {
    // When
    IsUsernameAvailableResponse result = userService.isUsernameAvailable(name);

    // Then
    assertFalse(result.isAvailable());
  }

}