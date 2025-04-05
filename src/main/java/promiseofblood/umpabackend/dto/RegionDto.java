package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entitiy.Region;

@Builder
@Getter
@AllArgsConstructor
public class RegionDto {

  private Long id;
  private String name;

  public static RegionDto of(
    Region region) {
    return RegionDto.builder()
      .id(region.getId())
      .name(region.getName())
      .build();
  }
}
