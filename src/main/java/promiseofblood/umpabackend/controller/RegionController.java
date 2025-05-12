package promiseofblood.umpabackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import promiseofblood.umpabackend.controller.common.RestControllerV1;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.dto.RegionCategoryDto;
import promiseofblood.umpabackend.dto.RegionDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerV1
public class RegionController {

  @GetMapping("/regions")
  public List<RegionCategoryDto> getRegions() {
    return Stream.of(RegionCategory.values())
      .map(regionCategory -> RegionCategoryDto.builder()
        .code(regionCategory.getCode())
        .name(regionCategory.getKoreanName())
        .regions(
          regionCategory.getRegions().stream()
            .map(region -> RegionDto.builder()
              .code(region.getCode())
              .name(region.getKoreanName())
              .build())
            .collect(Collectors.toList())
        )
        .build()
      )
      .collect(Collectors.toList());
  }
}
