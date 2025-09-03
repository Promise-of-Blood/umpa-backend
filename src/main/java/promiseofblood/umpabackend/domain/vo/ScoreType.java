package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScoreType {
  FULL_SCORE("풀스코어", "composition"),

  VOCAL("보컬곡", "vocal"),

  PIANO("피아노", "piano"),

  GUITAR("기타", "guitar"),

  BASS("베이스", "bass"),

  WIND_INSTRUMENT("관악", "wind_instrument"),

  DRUM("드럼", "drum");

  private final String koreanName;

  private final String assetName;
}
