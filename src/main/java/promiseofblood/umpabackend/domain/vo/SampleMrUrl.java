package promiseofblood.umpabackend.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleMrUrl {

  private String url;

  public static SampleMrUrl of(String url) {
    return new SampleMrUrl(url);
  }

  @Override
  public String toString() {
    return url;
  }
}
