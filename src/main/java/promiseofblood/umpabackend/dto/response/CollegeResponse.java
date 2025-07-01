package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;

@Getter
@Builder
public class CollegeResponse {

  private String code;

  private String name;

  public static CollegeResponse of(College college) {
    return CollegeResponse.builder()
      .code(college.name())
      .name(college.getKoreanName())
      .build();
  }
}
