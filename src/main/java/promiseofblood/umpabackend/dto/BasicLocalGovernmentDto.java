package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.BasicLocalGovernment;

@Builder
@Getter
@AllArgsConstructor
public class BasicLocalGovernmentDto {
  private Long id;
  private String name;

  public static BasicLocalGovernmentDto of(BasicLocalGovernment basicLocalGovernment) {
    return BasicLocalGovernmentDto.builder()
            .id(basicLocalGovernment.getId())
            .name(basicLocalGovernment.getName())
            .build();
  }
}
