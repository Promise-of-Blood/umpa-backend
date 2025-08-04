package promiseofblood.umpabackend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;


public class ConstantDto {

//  public static interface EnumWrapper<T extends Enum<T>> {
//    String getCode();
//
//    String getName();
//
//  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class GradeResponse {

    private String code;

    private String name;

    public static GradeResponse from(Grade grade) {
      return GradeResponse.builder()
        .code(grade.name())
        .name(grade.getKoreanName())
        .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CollegeResponse {

    private String code;

    private String name;

    public static CollegeResponse from(College college) {
      return CollegeResponse.builder()
        .code(college.name())
        .name(college.getKoreanName())
        .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class WeekdayResponse {

    private String code;

    private String name;

    public static WeekdayResponse from(WeekDay weekday) {
      return WeekdayResponse.builder()
        .code(weekday.name())
        .name(weekday.getKoreanName())
        .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class SubjectResponse {

    private String code;

    private String name;

    public static SubjectResponse of(Subject subject) {
      return SubjectResponse.builder()
        .code(subject.name())
        .name(subject.name())
        .build();
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
      return MajorResponse.builder()
        .code(major.name())
        .name(major.getKoreanName())
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
}
