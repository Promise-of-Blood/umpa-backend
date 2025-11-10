package promiseofblood.umpabackend.web.schema.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.DurationRange;

/** 서비스 평균 소요 기간 DTO */
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveAverageDurationResponse {

  private int minValue;

  private String minUnit;

  private int maxValue;

  private String maxUnit;

  public static RetrieveAverageDurationResponse from(DurationRange durationRange) {
    return RetrieveAverageDurationResponse.builder()
        .minValue(durationRange.getMinValue())
        .minUnit(durationRange.getMinUnit().getKoreanName())
        .maxValue(durationRange.getMaxValue())
        .maxUnit(durationRange.getMaxUnit().getKoreanName())
        .build();
  }
}
