package promiseofblood.umpabackend.domain.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.domain.vo.Username;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired private UserRepository subject;

  @Test
  void existsByUsername() {
    // Given
    Username username = new Username("test");
    User user =
        User.builder()
            .loginId("loginId")
            .role(Role.USER)
            .userStatus(UserStatus.ACTIVE)
            .username(username)
            .build();

    subject.save(user);

    // When
    boolean isUsernameExisted = subject.existsByUsername(username);

    // Then
    Assertions.assertTrue(isUsernameExisted);
  }
}
