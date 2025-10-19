package promiseofblood.umpabackend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.DurationRange;

public class ServicePostDto {

  /** 서비스 평균 소요 기간 DTO */
  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class AverageDurationDto {

    private int minValue;

    private String minUnit;

    private int maxValue;

    private String maxUnit;

    public static AverageDurationDto from(DurationRange durationRange) {
      return AverageDurationDto.builder()
          .minValue(durationRange.getMinValue())
          .minUnit(durationRange.getMinUnit().getKoreanName())
          .maxValue(durationRange.getMaxValue())
          .maxUnit(durationRange.getMaxUnit().getKoreanName())
          .build();
    }
  }
}
