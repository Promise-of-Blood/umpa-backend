package promiseofblood.umpabackend.domain.vo;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Major implements EnumVoType {

  PIANO("피아노", "piano"),

  COMPOSITION("작곡", "composition"),

  VOCAL("보컬", "vocal"),

  DRUM("드럼", "drum"),

  BASS("베이스", "bass"),

  GUITAR("기타", "guitar"),

  ELECTRONIC_MUSIC("전자음악", "electronic_music"),

  WIND_INSTRUMENT("관악", "wind_instrument");

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
