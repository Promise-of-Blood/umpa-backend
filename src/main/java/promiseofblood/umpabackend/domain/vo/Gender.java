package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements EnumVoType {
  MALE("남자"),

  FEMALE("여자");

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
