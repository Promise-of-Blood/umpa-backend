package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleStatus implements EnumVoType {
  DRAFT("임시저장"),

  PUBLISHED("발행");

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
