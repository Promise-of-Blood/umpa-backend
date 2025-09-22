package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.application.query.ConstantListQuery;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantResponses {

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class GradeResponse {

    private String code;

    private String name;

    public static GradeResponse from(Grade grade) {
      return GradeResponse.builder().code(grade.name()).name(grade.getKoreanName()).build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CollegeResponse {

    private String code;

    private String name;

    public static CollegeResponse from(College college) {
      return CollegeResponse.builder().code(college.name()).name(college.getKoreanName()).build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class WeekdayResponse {

    private String code;

    private String name;

    public static WeekdayResponse from(WeekDay weekday) {
      return WeekdayResponse.builder().code(weekday.name()).name(weekday.getKoreanName()).build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class SubjectResponse {

    private String code;

    private String name;

    public static SubjectResponse from(Subject subject) {
      return SubjectResponse.builder().code(subject.name()).name(subject.getKoreanName()).build();
    }
  }

  @Getter
  @Builder
  public static class SubjectIconResponse {

    private String code;

    private String name;

    private String svg;

    public static SubjectIconResponse from(ConstantListQuery.Result result) {
      return SubjectIconResponse.builder()
          .code(result.code())
          .name(result.name())
          .svg(result.svg())
          .build();
    }
  }

  @Builder
  @Getter
  public static class ScoreTypeIconResponse {

    private String code;

    private String name;

    private String svg;

    public static ScoreTypeIconResponse from(ConstantListQuery.Result result) {
      return ScoreTypeIconResponse.builder()
          .code(result.code())
          .name(result.name())
          .svg(result.svg())
          .build();
    }
  }

  @Builder
  @Getter
  public static class MajorResponse {

    private String code;

    private String name;

    public static MajorResponse from(Major major) {
      return MajorResponse.builder().code(major.name()).name(major.getKoreanName()).build();
    }
  }

  @Builder
  @Getter
  public static class MajorIconResponse {

    private String code;

    private String name;

    private String svg;

    public static MajorIconResponse from(ConstantListQuery.Result result) {
      return MajorIconResponse.builder()
          .code(result.code())
          .name(result.name())
          .svg(result.svg())
          .build();
    }
  }

  @Builder
  @Getter
  public static class InstrumentResponse {

    private String code;

    private String name;

    public static InstrumentResponse from(Instrument instrument) {
      return InstrumentResponse.builder()
          .code(instrument.name())
          .name(instrument.getKoreanName())
          .build();
    }
  }

  @Builder
  @Getter
  public static class InstrumentIconResponse {

    private String code;

    private String name;

    private String svg;

    public static InstrumentIconResponse from(ConstantListQuery.Result result) {
      return InstrumentIconResponse.builder()
          .code(result.code())
          .name(result.name())
          .svg(result.svg())
          .build();
    }
  }

  @Getter
  @Builder
  public static class RegionCategoryResponse {

    private String code;

    private String name;

    private List<RegionResponse> regions;

    public static RegionCategoryResponse from(RegionCategory regionCategory) {
      return RegionCategoryResponse.builder()
          .code(regionCategory.getCode())
          .name(regionCategory.getKoreanName())
          .regions(regionCategory.getRegions().stream().map(RegionResponse::from).toList())
          .build();
    }
  }

  @Builder
  @Getter
  public static class RegionResponse {

    private String code;

    private String name;

    public static RegionResponse from(Region region) {
      return RegionResponse.builder().code(region.getCode()).name(region.getName()).build();
    }
  }
}
