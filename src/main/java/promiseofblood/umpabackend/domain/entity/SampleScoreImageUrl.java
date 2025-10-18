package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleScoreImageUrl {

  private String url;

  public static SampleScoreImageUrl of(String url) {
    return new SampleScoreImageUrl(url);
  }

  @Override
  public String toString() {
    return url;
  }
}
