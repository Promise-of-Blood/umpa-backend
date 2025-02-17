package promiseofblood.umpabackend.dto;

import lombok.*;
import promiseofblood.umpabackend.domain.College;

@Builder
@Getter
@AllArgsConstructor
public class CollegeDto {
  private Long id;
  private String name;

  public static CollegeDto of(College college) {
    return CollegeDto.builder().id(college.getId()).name(college.getName()).build();
  }
}
