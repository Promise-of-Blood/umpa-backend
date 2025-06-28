package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Instrument {

  PIANO("피아노"),

  VOCAL("보컬"),

  COMPOSITION("작곡"),

  DRUM("드럼"),

  GUITAR("기타"),

  BASS("베이스"),

  WIND_INSTRUMENT("관악");

  private final String koreanName;

}
