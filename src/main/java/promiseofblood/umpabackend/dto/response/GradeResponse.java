package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.Grade;

@Builder
@Getter
public class GradeResponse {

  private String code;

  private String name;

  public static GradeResponse of(Grade grade) {
    return GradeResponse.builder()
      .code(grade.name())
      .name(grade.getKoreanName())
      .build();
  }
}
