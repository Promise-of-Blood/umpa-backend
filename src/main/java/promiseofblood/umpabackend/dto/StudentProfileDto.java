package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.dto.ConstantDto.MajorResponse;

@Getter
public class StudentProfileDto {

  @Getter
  @Builder
  @AllArgsConstructor
  public static class StudentProfileRequest {

    @Schema(description = "전공", example = "PIANO")
    private Major major;

    @Schema(
        description = "선호하는 학교",
        example = "[\"GANGDONG_UNIVERSITY\", \"KYONGBUK_SCIENCE_COLLEGE\"]")
    private List<College> preferredColleges;

    @Schema(description = "학년", example = "HIGH_3")
    private Grade grade;
  }

  @Getter
  @Builder(access = lombok.AccessLevel.PRIVATE)
  public static class StudentProfileResponse {

    private MajorResponse major;

    private List<ConstantDto.CollegeResponse> preferredColleges;

    private ConstantDto.GradeResponse grade;

    public static StudentProfileResponse from(StudentProfile studentProfile) {

      List<ConstantDto.CollegeResponse> preferredCollegeDtoList = new ArrayList<>();
      List<College> preferredColleges = studentProfile.getPreferredColleges();
      if (preferredColleges != null) {
        for (College college : preferredColleges) {
          preferredCollegeDtoList.add(ConstantDto.CollegeResponse.from(college));
        }
      }

      return StudentProfileResponse.builder()
          .major(Optional.ofNullable(studentProfile.getMajor())
            .map(MajorResponse::from)
            .orElse(null))
          .preferredColleges(preferredCollegeDtoList)
          .grade(Optional.ofNullable(studentProfile.getGrade())
            .map(ConstantDto.GradeResponse::from)
            .orElse(null))
          .build();
    }
  }
}
