package promiseofblood.umpabackend.dto.response;

import java.time.YearMonth;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.dto.ConstantDto;

@Getter
@Builder
public class TeacherProfileResponse {

  @Getter
  @Builder
  public static class TeacherCareerResponse {

    private boolean isRepresentative;

    private String title;

    private YearMonth start;

    private YearMonth end;
  }

  private String description;

  private ConstantDto.MajorResponse major;

  private RegionResponse lessonRegion;

  private List<TeacherCareerResponse> careers;

  private List<String> links;

  public static TeacherProfileResponse from(TeacherProfile teacherProfile) {
    List<TeacherCareerResponse> careerResponses = teacherProfile.getCareers() == null
      ? null
      : teacherProfile.getCareers().stream()
        .map(career -> TeacherCareerResponse.builder()
          .isRepresentative(career.isRepresentative())
          .title(career.getTitle())
          .start(career.getStart())
          .end(career.getEnd())
          .build())
        .toList();

    List<String> linkResponses = teacherProfile.getLinks() == null
      ? List.of()
      : teacherProfile.getLinks().stream()
        .map(TeacherLink::getLink)
        .toList();

    return TeacherProfileResponse.builder()
      .description(teacherProfile.getDescription())
      .major(ConstantDto.MajorResponse.from(teacherProfile.getMajor()))
      .lessonRegion(RegionResponse.from(teacherProfile.getLessonRegion()))
      .careers(careerResponses)
      .links(linkResponses)
      .build();
  }
}
