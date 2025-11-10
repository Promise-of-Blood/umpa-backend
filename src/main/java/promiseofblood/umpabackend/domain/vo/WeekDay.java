package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeekDay implements EnumVoType {
  MONDAY("월요일"),

  TUESDAY("화요일"),

  WEDNESDAY("수요일"),

  THURSDAY("목요일"),

  FRIDAY("금요일"),

  SATURDAY("토요일"),

  SUNDAY("일요일");

  private final String koreanName;

  @Override
  public String getName() {
    return this.getKoreanName();
  }

  @Override
  public String getCode() {
    return this.name();
  }
}
