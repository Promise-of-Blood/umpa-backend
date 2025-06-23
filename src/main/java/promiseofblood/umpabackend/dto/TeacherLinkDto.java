package promiseofblood.umpabackend.dto;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.TeacherLink;

@Builder
@Getter
public class TeacherLinkDto {

  private String link;

  public static TeacherLinkDto of(TeacherLink teacherLink) {
    return TeacherLinkDto.builder()
      .link(teacherLink.getLink())
      .build();
  }

}
