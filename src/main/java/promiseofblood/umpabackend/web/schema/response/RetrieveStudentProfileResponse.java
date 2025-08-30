package promiseofblood.umpabackend.web.schema.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.CollegeResponse;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.MajorResponse;

@Getter
@Builder(access = lombok.AccessLevel.PRIVATE)
public class RetrieveStudentProfileResponse {

  private MajorResponse major;

  private List<CollegeResponse> preferredColleges;

  private ConstantResponses.GradeResponse grade;

  public static RetrieveStudentProfileResponse from(StudentProfile studentProfile) {

    List<CollegeResponse> preferredColleges = new ArrayList<>();
    for (College college : studentProfile.getPreferredColleges()) {
      preferredColleges.add(CollegeResponse.from(college));
    }

    return RetrieveStudentProfileResponse.builder()
      .major(MajorResponse.from(studentProfile.getMajor()))
      .preferredColleges(preferredColleges)
      .grade(ConstantResponses.GradeResponse.from(studentProfile.getGrade()))
      .build();
  }
}
