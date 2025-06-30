package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.Major;

@Builder
@Getter
public class MajorResponse {

  private String code;

  private String name;

  public static MajorResponse of(Major major) {
    return MajorResponse.builder()
      .code(major.name())
      .name(major.getKoreanName())
      .build();
  }

}
