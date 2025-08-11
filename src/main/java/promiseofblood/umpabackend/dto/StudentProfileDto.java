package promiseofblood.umpabackend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.dto.ConstantDto.MajorResponse;

@Getter
public class StudentProfileDto {

  @Getter
  @Builder(access = lombok.AccessLevel.PRIVATE)
  public static class StudentProfileResponse {

    private MajorResponse major;
    private List<ConstantDto.CollegeResponse> preferredColleges;
    private ConstantDto.GradeResponse grade;

    public static StudentProfileResponse from(StudentProfile studentProfile) {

      List<ConstantDto.CollegeResponse> preferredColleges = new ArrayList<>();
      for (College college : studentProfile.getPreferredColleges()) {
        preferredColleges.add(ConstantDto.CollegeResponse.from(college));
      }

      return StudentProfileResponse.builder()
        .major(MajorResponse.from(studentProfile.getMajor()))
        .preferredColleges(preferredColleges)
        .grade(ConstantDto.GradeResponse.from(studentProfile.getGrade()))
        .build();
    }
  }
}
