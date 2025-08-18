package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

public class ConstantDto {

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
      return SubjectResponse.builder().code(subject.name()).name(subject.name()).build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class LessonStyleResponse {

    private String code;

    private String name;

    public static LessonStyleResponse from(LessonStyle lessonStyle) {
      return LessonStyleResponse.builder()
        .code(lessonStyle.name())
        .name(lessonStyle.getKoreanName())
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
      return RegionResponse.builder().code(region.getCode()).name(region.getKoreanName()).build();
    }
  }
}
