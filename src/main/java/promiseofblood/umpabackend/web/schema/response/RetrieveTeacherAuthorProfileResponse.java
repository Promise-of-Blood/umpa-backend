package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;

/** 선생님 프로필 응답 형식 DTO */
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveTeacherAuthorProfileResponse {

  private String loginId;

  @Schema(nullable = true)
  private String username;

  private String keyphrase;

  private String profileImageUrl;

  private String description;

  private List<ListTeacherCareerResponse> careers;

  private List<ListTeacherLinkResponse> links;

  public static RetrieveTeacherAuthorProfileResponse from(User user) {

    TeacherProfile teacherProfile = user.getTeacherProfile();

    List<ListTeacherCareerResponse> careers = new ArrayList<>();
    for (TeacherCareer career : teacherProfile.getCareers()) {
      careers.add(ListTeacherCareerResponse.from(career));
    }

    List<ListTeacherLinkResponse> links = new ArrayList<>();
    for (TeacherLink link : teacherProfile.getLinks()) {
      links.add(ListTeacherLinkResponse.from(link));
    }

    return RetrieveTeacherAuthorProfileResponse.builder()
        .loginId(user.getLoginId())
        .username(user.getUsername().getValue())
        .keyphrase(teacherProfile.getKeyphrase())
        .profileImageUrl(user.getProfileImageUrl())
        .description(teacherProfile.getDescription())
        .careers(careers)
        .links(links)
        .build();
  }
}
