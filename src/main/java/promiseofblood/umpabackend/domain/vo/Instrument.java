package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Instrument {
  PIANO("피아노", "piano"),

  VOCAL("보컬", "vocal"),

  COMPOSITION("작곡", "composition"),

  DRUM("드럼", "drum"),

  GUITAR("기타", "guitar"),

  BASS("베이스", "bass"),

  WIND_INSTRUMENT("관악", "wind_instrument");

  private final String koreanName;

  private final String assetName;
}
