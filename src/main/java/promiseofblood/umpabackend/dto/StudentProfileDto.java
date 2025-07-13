package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.dto.response.CollegeResponse;
import promiseofblood.umpabackend.dto.response.GradeResponse;
import promiseofblood.umpabackend.dto.response.LessonStyleResponse;
import promiseofblood.umpabackend.dto.response.MajorResponse;
import promiseofblood.umpabackend.dto.response.SubjectResponse;

@Getter
@Builder
public class StudentProfileDto {

  private MajorResponse major;
  private LessonStyleResponse lessonStyle;
  private List<CollegeResponse> preferredColleges;
  private GradeResponse grade;
  private SubjectResponse subject;
  private List<WeekDay> weekDays;
  private String lessonRequirements;

  public static StudentProfileDto of(StudentProfile studentProfile) {
    return StudentProfileDto.builder()
      .major(MajorResponse.from(studentProfile.getMajor()))
      .lessonStyle(LessonStyleResponse.of(studentProfile.getLessonStyle()))
      .preferredColleges(studentProfile.getPreferredColleges().stream()
        .map(CollegeResponse::of)
        .toList()
      )
      .grade(GradeResponse.of(studentProfile.getGrade()))
      .subject(SubjectResponse.of(studentProfile.getSubject()))
      .weekDays(studentProfile.getWeekDays())
      .lessonRequirements(studentProfile.getLessonRequirements())
      .build();
  }

}
