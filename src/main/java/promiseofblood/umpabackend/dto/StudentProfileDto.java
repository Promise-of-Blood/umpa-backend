package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.dto.ConstantDto.MajorResponse;

@Getter
@Builder
public class StudentProfileDto {

  private MajorResponse major;
  private List<ConstantDto.CollegeResponse> preferredColleges;
  private ConstantDto.GradeResponse grade;

  public static StudentProfileDto from(StudentProfile studentProfile) {
    return StudentProfileDto.builder()
      .major(MajorResponse.from(studentProfile.getMajor()))
      .preferredColleges(studentProfile.getPreferredColleges().stream()
        .map(ConstantDto.CollegeResponse::from)
        .toList()
      )
      .grade(ConstantDto.GradeResponse.from(studentProfile.getGrade()))
      .build();
  }

}
