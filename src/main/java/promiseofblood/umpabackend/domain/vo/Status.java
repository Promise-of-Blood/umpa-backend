package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

  ACTIVE("활성"),

  INACTIVE("비활성");

  private final String koreanName;
}
