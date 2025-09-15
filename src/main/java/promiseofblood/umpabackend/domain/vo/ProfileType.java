package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileType implements EnumVoType {
  TEACHER("선생님"),

  STUDENT("학생");

  private final String koreanName;

  @Override
  public String getName() {
    return this.getCode();
  }

  @Override
  public String getCode() {
    return this.getKoreanName();
  }
}
