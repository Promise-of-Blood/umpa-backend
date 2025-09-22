package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumVoType {
  ADMIN("관리자"),

  STAFF("스태프"),

  USER("일반 사용자");

  private final String koreanName;

  @Override
  public String getName() {
    return this.name();
  }

  @Override
  public String getCode() {
    return this.getKoreanName();
  }
}
