package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.dto.response.GradeResponse;
import promiseofblood.umpabackend.dto.response.MajorResponse;

@Getter
@Builder
public class StudentProfileDto {

  private MajorResponse major;
  private List<ConstantDto.CollegeResponse> preferredColleges;
  private GradeResponse grade;

  public static StudentProfileDto of(StudentProfile studentProfile) {
    return StudentProfileDto.builder()
      .major(MajorResponse.from(studentProfile.getMajor()))
      .preferredColleges(studentProfile.getPreferredColleges().stream()
        .map(ConstantDto.CollegeResponse::from)
        .toList()
      )
      .grade(GradeResponse.of(studentProfile.getGrade()))
      .build();
  }

}
