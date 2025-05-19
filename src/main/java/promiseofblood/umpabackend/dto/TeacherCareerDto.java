package promiseofblood.umpabackend.dto;


import java.time.YearMonth;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entitiy.TeacherCareer;

@Getter
@Builder
@ToString
public class TeacherCareerDto {

  private boolean isRepresentative;

  private String title;

  private YearMonth start;

  private YearMonth end;

  public static TeacherCareerDto of(TeacherCareer teacherCareer) {
    return TeacherCareerDto.builder()
      .isRepresentative(teacherCareer.isRepresentative())
      .title(teacherCareer.getTitle())
      .start(teacherCareer.getStart())
      .end(teacherCareer.getEnd())
      .build();
  }
}
