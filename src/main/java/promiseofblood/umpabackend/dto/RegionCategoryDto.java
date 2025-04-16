package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.*;

@Getter
@Builder
public class RegionCategoryDto {

  private String code;

  private String name;
  
  private List<RegionDto> regions;

}
