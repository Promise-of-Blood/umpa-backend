package promiseofblood.umpabackend.domain.vo;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum DurationUnit implements EnumVoType {
  DAY("일"),
  WEEK("주"),
  MONTH("월");

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
