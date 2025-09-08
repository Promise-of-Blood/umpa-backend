package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(nullable = true)
  private MajorResponse major;

  private List<CollegeResponse> preferredColleges;

  @Schema(nullable = true)
  private ConstantResponses.GradeResponse grade;

  public static RetrieveStudentProfileResponse from(StudentProfile studentProfile) {

    List<CollegeResponse> preferredColleges = new ArrayList<>();
    for (College college : studentProfile.getPreferredColleges()) {
      preferredColleges.add(CollegeResponse.from(college));
    }

    return RetrieveStudentProfileResponse.builder()
        .major(
            studentProfile.getMajor() != null
                ? MajorResponse.from(studentProfile.getMajor())
                : null)
        .preferredColleges(preferredColleges)
        .grade(
            studentProfile.getGrade() != null
                ? ConstantResponses.GradeResponse.from(studentProfile.getGrade())
                : null)
        .build();
  }
}
