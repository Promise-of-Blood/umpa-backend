package promiseofblood.umpabackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.user.entitiy.Major;

@Builder
@Getter
@AllArgsConstructor
public class MajorDto {
  private Long id;
  private String name;

  public static MajorDto of(Major major) {
    return MajorDto.builder().id(major.getId()).name(major.getName()).build();
  }
}
