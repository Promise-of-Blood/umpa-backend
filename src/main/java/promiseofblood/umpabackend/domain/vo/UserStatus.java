package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements EnumVoType {

  // 1. 회원가입만 해 두고, 프로필은 아직 완료되지 않은 경우
  PENDING("대기"),

  // 2. 프로필이 완료되어, 서비스 이용이 가능한 경우
  ACTIVE("활성"),

  // 3. 탈퇴한 경우
  WITHDRAWN("탈퇴"),

  // 4. 관리자에 의해 정지된 경우
  SUSPENDED("정지");

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
