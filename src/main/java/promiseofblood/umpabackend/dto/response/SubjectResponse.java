package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.Subject;

@Builder
@Getter
public class SubjectResponse {

  private String code;

  private String name;

  public static SubjectResponse of(Subject subject) {
    return SubjectResponse.builder()
      .code(subject.name())
      .name(subject.name())
      .build();
  }

}
