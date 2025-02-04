package promiseofblood.umpabackend.user.dto.region;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import promiseofblood.umpabackend.user.entitiy.region.RegionalLocalGovernment;


@Builder
@Getter
@AllArgsConstructor
public class RegionalLocalGovernmentDto {

  private Long id;
  private String name;
  private List<BasicLocalGovernmentDto> items;

  public static RegionalLocalGovernmentDto of(RegionalLocalGovernment region) {
    return RegionalLocalGovernmentDto.builder()
            .id(region.getId())
            .name(region.getName())
            .items(region.getBasicLocalGovernments().stream()
                    .map(BasicLocalGovernmentDto::of)
                    .collect(Collectors.toList()))
            .build();
  }
}
