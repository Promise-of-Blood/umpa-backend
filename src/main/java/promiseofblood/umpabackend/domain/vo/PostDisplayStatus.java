package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostDisplayStatus implements EnumVoType {
  PUBLISHED("모집중"),
  PAUSED("모집 중단"),
  OWNER_MISSING("작성자 탈퇴"),
  DELETED("삭제됨");

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
