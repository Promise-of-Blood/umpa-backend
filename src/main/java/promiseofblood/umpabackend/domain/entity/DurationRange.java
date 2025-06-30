package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.domain.vo.DurationUnit;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationRange {

  private int minValue;
  @Enumerated(EnumType.STRING)
  private DurationUnit minUnit;

  private int maxValue;
  @Enumerated(EnumType.STRING)
  private DurationUnit maxUnit;

}