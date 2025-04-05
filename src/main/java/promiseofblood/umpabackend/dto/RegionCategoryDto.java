package promiseofblood.umpabackend.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import promiseofblood.umpabackend.domain.entitiy.RegionCategory;


@Builder
@Getter
@AllArgsConstructor
public class RegionCategoryDto {

  private Long id;
  private String name;
  private List<RegionDto> items;

  public static RegionCategoryDto of(RegionCategory region) {
    return RegionCategoryDto.builder()
      .id(region.getId())
      .name(region.getName())
      .items(region.getRegions().stream()
        .map(RegionDto::of)
        .collect(Collectors.toList()))
      .build();
  }
}
