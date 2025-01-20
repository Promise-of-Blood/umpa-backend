package promiseofblood.umpabackend.user.dto;

import lombok.*;
import promiseofblood.umpabackend.user.entitiy.College;

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
