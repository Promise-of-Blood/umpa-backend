package kr.co.umpabackend.domain.repository;

import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.UserStatus;
import kr.co.umpabackend.domain.vo.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
