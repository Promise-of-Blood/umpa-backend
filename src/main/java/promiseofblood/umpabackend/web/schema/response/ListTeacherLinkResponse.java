package promiseofblood.umpabackend.web.schema.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.TeacherLink;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class ListTeacherLinkResponse {

  private long id;

  private String link;

  public static ListTeacherLinkResponse from(TeacherLink teacherLink) {
    return ListTeacherLinkResponse.builder().id(teacherLink.getId()).link(teacherLink.getLink())
      .build();
  }
}
