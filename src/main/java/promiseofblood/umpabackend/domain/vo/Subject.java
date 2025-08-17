package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject {
  PIANO("피아노"),

  COMPOSITION("작곡"),

  VOCAL("보컬"),

  DRUM("드럼"),

  BASS("베이스"),

  GUITAR("기타"),

  ELECTRONIC_MUSIC("전자음악"),

  WIND_INSTRUMENT("관악"),

  TRADITIONAL_HARMONY("전통화성학"),

  SIGHT_SINGING("시창청음"),

  PRACTICAL_HARMONY("실용 화성학"),

  SCORE_PRODUCTION("악보제작"),

  ACCOMPANIST("반주자"),

  MR_PRODUCTION("MR제작");

  private final String koreanName;
}
