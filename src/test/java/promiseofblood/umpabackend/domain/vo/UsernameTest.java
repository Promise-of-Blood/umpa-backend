package promiseofblood.umpabackend.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsernameTest {

  @ParameterizedTest
  @DisplayName("유효한 사용자 이름은 생성될 수 있다.")
  @ValueSource(strings = {"홍길동", "abc", "user123", "테스트1", "ABCDEFGH"})
  // 8자 이하, 허용 문자
  void valid_usernames_should_be_created(String value) {
    Username username = new Username(value);
    assertThat(username.getValue()).isEqualTo(value);
  }

  @ParameterizedTest
  @DisplayName("유효하지 않은 사용자 이름은 예외를 던진다.")
  @ValueSource(strings = {"", "ABCDEFGHIJ", "John_Doe", "테스트!", "123456789", " "})
  void invalid_usernames_should_throw_exception(String value) {
    assertThatThrownBy(() -> new Username(value)).isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @DisplayName("닉네임의 값이 같다면, 동일한 객체로 간주한다.")
  @ValueSource(strings = {"홍길동", "abc123"})
  void same_value_usernames_are_equal(String value) {
    Username u1 = new Username(value);
    Username u2 = new Username(value);

    assertThat(u1).isEqualTo(u2);
    assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
  }
}
