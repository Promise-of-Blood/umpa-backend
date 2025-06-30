package promiseofblood.umpabackend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;

@Builder
@Getter
public class TeacherProfileDto {

  private Major major;

  private Region lessonRegion;

  private List<TeacherCareerDto> careers;

  private List<String> links;

  public static TeacherProfileDto of(TeacherProfile teacherProfile) {
    List<TeacherCareerDto> teacherCareerDtos = teacherProfile.getCareers() == null
      ? null
      : teacherProfile.getCareers().stream()
        .map(TeacherCareerDto::of)
        .toList();

    List<TeacherLinkDto> teacherLinkDtos = teacherProfile.getLinks() == null
      ? null
      : teacherProfile.getLinks().stream()
        .map(TeacherLinkDto::of)
        .toList();

    return TeacherProfileDto.builder()
      .major(teacherProfile.getMajor())
      .lessonRegion(teacherProfile.getLessonRegion())
      .careers(teacherCareerDtos)
      .links(
        teacherLinkDtos == null
          ? new ArrayList<>()
          : teacherLinkDtos.stream()
            .map(TeacherLinkDto::getLink)
            .toList()
      )
      .build();
  }

}
