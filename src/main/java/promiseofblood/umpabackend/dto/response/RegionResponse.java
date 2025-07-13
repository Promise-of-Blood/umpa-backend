package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.Region;

@Builder
@Getter
public class RegionResponse {

  private String code;

  private String name;

  public static RegionResponse from(Region region) {
    return RegionResponse.builder()
      .code(region.getCode())
      .name(region.getKoreanName())
      .build();
  }

}
