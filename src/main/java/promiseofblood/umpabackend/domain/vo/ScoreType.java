package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScoreType implements EnumVoType {
  FULL_SCORE("풀스코어", "full_score"),

  VOCAL("보컬곡", "vocal"),

  PIANO("피아노", "piano"),

  GUITAR("기타", "guitar"),

  BASS("베이스", "bass"),

  WIND_INSTRUMENT("관악", "wind_instrument"),

  DRUM("드럼", "drum");

  private final String koreanName;

  private final String assetName;

  @Override
  public String getName() {
    return this.getCode();
  }

  @Override
  public String getCode() {
    return this.getKoreanName();
  }
}
