package kr.co.umpabackend.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  public static DurationRange of(String averageDuration) {
    String[] parts = averageDuration.split("~");
    String[] minParts = parts[0].split("(?<=\\d)(?=\\D)");
    String[] maxParts = parts[1].split("(?<=\\d)(?=\\D)");

    return DurationRange.builder()
        .minValue(Integer.parseInt(minParts[0]))
        .minUnit(DurationUnit.valueOf(minParts[1]))
        .maxValue(Integer.parseInt(maxParts[0]))
        .maxUnit(DurationUnit.valueOf(maxParts[1]))
        .build();
  }
}
