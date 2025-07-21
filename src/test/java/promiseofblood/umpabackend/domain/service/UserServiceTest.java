package promiseofblood.umpabackend.domain.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promiseofblood.umpabackend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("isUsernameAvailable 메서드는 닉네임이 중복되었을 경우 false를 반환한다.")
  void isUsernameAvailable_UsernameExists_ReturnsTrue() {
    String username = "existing-user";
    when(userRepository.existsByUsername(username)).thenReturn(true);

    boolean isAvailable = userService.isUsernameAvailable(username);

    assertFalse(isAvailable, "Username은 이미 존재하므로 사용 불가능해야 합니다.");
  }

  @Test
  @DisplayName("isUsernameAvailable 메서드는 닉네임이 중복되지 않았을 경우 true를 반환한다.")
  void isUsernameAvailable_UsernameDoesNotExist_ReturnsFalse() {
    String username = "new-user";
    when(userRepository.existsByUsername(username)).thenReturn(false);

    boolean isAvailable = userService.isUsernameAvailable(username);

    assertTrue(isAvailable, "Username은 존재하지 않으므로 사용 가능해야 합니다.");
  }
}