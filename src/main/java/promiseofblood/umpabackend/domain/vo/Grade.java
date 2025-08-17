package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
  MIDDLE_3("중3"),

  HIGH_1("고1"),

  HIGH_2("고2"),

  HIGH_3("고3"),

  REPEATER("n수생"),

  UNIVERSITY("대학생"),

  WORKER("사회인");

  private final String koreanName;

}
