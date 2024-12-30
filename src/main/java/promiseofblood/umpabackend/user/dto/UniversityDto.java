package promiseofblood.umpabackend.user.dto;

import lombok.*;
import promiseofblood.umpabackend.user.entitiy.University;

@Builder
@Getter
@AllArgsConstructor
public class UniversityDto {

  private Long id;
  private String name;

  public static UniversityDto of(University university) {
    return UniversityDto.builder().id(university.getId()).name(university.getName()).build();
  }
}
