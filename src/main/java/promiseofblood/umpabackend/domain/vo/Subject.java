package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject implements EnumVoType {
  PIANO("피아노", "piano"),

  COMPOSITION("작곡", "composition"),

  VOCAL("보컬", "vocal"),

  DRUM("드럼", "drum"),

  BASS("베이스", "bass"),

  GUITAR("기타", "guitar"),

  ELECTRONIC_MUSIC("전자음악", "electronic_music"),

  WIND_INSTRUMENT("관악", "wind_instrument"),

  TRADITIONAL_HARMONY("전통화성학", "traditional_harmony"),

  SIGHT_SINGING("시창청음", "sight_singing"),

  PRACTICAL_HARMONY("실용 화성학", "_harmony"),

  SCORE_PRODUCTION("악보제작", "score_production"),

  ACCOMPANIST("반주자", "accompaniment"),

  MR_PRODUCTION("MR제작", "mr_production");

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
