package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PracticeLocation implements EnumVoType {
  RENTAL_STUDIO("시간제 연습실"),

  PRIVATE_STUDIO("개인 작업실"),

  MUTUAL_CONSULTATION("협의");

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
