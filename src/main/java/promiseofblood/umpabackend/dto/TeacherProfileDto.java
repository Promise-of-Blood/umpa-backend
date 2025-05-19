package promiseofblood.umpabackend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entitiy.TeacherProfile;

@Builder
@Getter
public class TeacherProfileDto {

  private String lessonRegion;

  private List<TeacherCareerDto> careers;

  private List<TeacherLinkDto> links;

  public static TeacherProfileDto of(TeacherProfile teacherProfile) {
    List<TeacherCareerDto> teacherCareerDtos = teacherProfile.getCareers().stream()
      .map(TeacherCareerDto::of)
      .toList();

    List<TeacherLinkDto> teacherLinkDtos = teacherProfile.getLinks().stream()
      .map(TeacherLinkDto::of)
      .toList();

    return TeacherProfileDto.builder()
      .lessonRegion(teacherProfile.getLessonRegion().name())
      .careers(teacherCareerDtos)
      .links(teacherLinkDtos)
      .build();
  }

}
