package promiseofblood.umpabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;

public class TeacherProfileDto {

  @Builder
  @Getter
  @AllArgsConstructor
  public static class TeacherProfileRequest {

    @Schema(
        description = "선생님 소개",
        example = "안녕하세요, 저는 전자 음악을 전공한 선생님입니다. 레슨을 통해 여러분과 함께 음악의 즐거움을 나누고 싶습니다.")
    private String description;

    @Schema(description = "선생님 전공", example = "ELECTRONIC_MUSIC")
    private Major major;

    @Schema(
        description = "경력",
        example =
            "["
                + "{\"title\":\"인덕대학교 컴퓨터소프트웨어학과 졸업\",\"start\":\"2020.03\",\"end\":\"2024.02\",\"isRepresentative\":true},"
                + "{\"title\":\"서울대학교 컴퓨터소프트웨어학과 졸업\",\"start\":\"2020.03\",\"end\":\"2024.02\",\"isRepresentative\":true}"
                + "]")
    private List<TeacherCareerRequest> careers;

    @Schema(example = "[\"https://gdsnadevlog.com\",\"https://www.gdsnadevlog.com\"]")
    private List<String> links;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TeacherCareerRequest {

      private String title;

      @DateTimeFormat(pattern = "yyyy.MM")
      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
      private YearMonth start;

      @DateTimeFormat(pattern = "yyyy.MM")
      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
      private YearMonth end;

      private boolean isRepresentative;
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherProfileResponse {

    private String description;

    private ConstantDto.MajorResponse major;

    private List<TeacherCareerDto> careers;

    private List<TeacherLinkDto> links;

    public static TeacherProfileResponse from(TeacherProfile teacherProfile) {

      List<TeacherCareerDto> teacherCareerDtoList = new ArrayList<>();
      for (TeacherCareer teacherCareer : teacherProfile.getCareers()) {
        teacherCareerDtoList.add(TeacherCareerDto.from(teacherCareer));
      }

      List<TeacherLinkDto> teacherLinkDtoList = new ArrayList<>();
      for (TeacherLink teacherLink : teacherProfile.getLinks()) {
        teacherLinkDtoList.add(TeacherLinkDto.from(teacherLink));
      }

      return TeacherProfileResponse.builder()
          .description(teacherProfile.getDescription())
          .major(ConstantDto.MajorResponse.from(teacherProfile.getMajor()))
          .careers(teacherCareerDtoList)
          .links(teacherLinkDtoList)
          .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherCareerDto {

    private long id;

    private boolean isRepresentative;

    private String title;

    private YearMonth start;

    private YearMonth end;

    public static TeacherCareerDto from(TeacherCareer teacherCareer) {
      return TeacherCareerDto.builder()
          .id(teacherCareer.getId())
          .isRepresentative(teacherCareer.isRepresentative())
          .title(teacherCareer.getTitle())
          .start(teacherCareer.getStart())
          .end(teacherCareer.getEnd())
          .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherLinkDto {

    private long id;

    private String link;

    public static TeacherLinkDto from(TeacherLink teacherLink) {
      return TeacherLinkDto.builder().id(teacherLink.getId()).link(teacherLink.getLink()).build();
    }
  }
}
