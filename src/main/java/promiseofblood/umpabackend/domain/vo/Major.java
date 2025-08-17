package promiseofblood.umpabackend.domain.vo;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Major {
  PIANO("피아노"),

  COMPOSITION("작곡"),

  VOCAL("보컬"),

  DRUM("드럼"),

  BASS("베이스"),

  GUITAR("기타"),

  ELECTRONIC_MUSIC("전자음악"),

  WIND_INSTRUMENT("관악");

  private final String koreanName;
}
