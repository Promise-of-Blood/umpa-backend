package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class ListTeacherCareerResponse {

  private long id;

  private boolean isRepresentative;

  private String title;

  @Schema(type = "string")
  private YearMonth start;

  @Schema(type = "string")
  private YearMonth end;

  public static ListTeacherCareerResponse from(TeacherCareer teacherCareer) {
    return ListTeacherCareerResponse.builder()
        .id(teacherCareer.getId())
        .isRepresentative(teacherCareer.isRepresentative())
        .title(teacherCareer.getTitle())
        .start(teacherCareer.getStart())
        .end(teacherCareer.getEnd())
        .build();
  }
}
