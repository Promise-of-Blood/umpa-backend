package promiseofblood.umpabackend.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.RegionCategory;

@Getter
@Builder
public class RegionCategoryResponse {

  private String code;

  private String name;

  private List<RegionResponse> regions;

  public static RegionCategoryResponse from(RegionCategory regionCategory) {
    return RegionCategoryResponse.builder()
      .code(regionCategory.getCode())
      .name(regionCategory.getKoreanName())
      .regions(regionCategory.getRegions().stream()
        .map(RegionResponse::from)
        .toList())
      .build();
  }

}
