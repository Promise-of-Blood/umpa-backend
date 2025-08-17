package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScoreType {
  FULL_SCORE("풀스코어"),

  VOCAL("보컬곡"),

  PIANO("피아노"),

  GUITAR("기타"),

  BASS("베이스"),

  WIND_INSTRUMENT("관악"),

  DRUM("드럼");

  private final String koreanName;
}
