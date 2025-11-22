package kr.co.umpabackend.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

  private static final String REGEX = "^[가-힣a-zA-Z0-9]{1,8}$";
  private static final Pattern PATTERN = Pattern.compile(REGEX);

  @Column(name = "username", nullable = false, unique = true)
  private String value;

  public Username(String value) {
    validate(value);
    this.value = value;
  }

  public static void validate(String value) {
    if (value == null || !PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("닉네임은 한글/영문/숫자로 1~8자까지 가능합니다.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Username username)) {
      return false;
    }
    return Objects.equals(value, username.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
