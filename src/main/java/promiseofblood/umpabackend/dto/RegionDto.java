package promiseofblood.umpabackend.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RegionDto {

  private String code;

  private String name;
}
