package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.MajorResponse;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class RetrieveTeacherProfileResponse {

  @Schema(nullable = true)
  private String keyphrase;

  @Schema(nullable = true)
  private String description;

  @Schema(nullable = true)
  private ConstantResponses.MajorResponse major;

  private List<ListTeacherCareerResponse> careers;

  private List<ListTeacherLinkResponse> links;

  public static RetrieveTeacherProfileResponse from(TeacherProfile teacherProfile) {

    List<ListTeacherCareerResponse> teacherCareerDtoList = new ArrayList<>();
    List<TeacherCareer> teacherCareers = teacherProfile.getCareers();
    if (teacherCareers != null) {
      for (TeacherCareer teacherCareer : teacherCareers) {
        teacherCareerDtoList.add(ListTeacherCareerResponse.from(teacherCareer));
      }
    }

    List<ListTeacherLinkResponse> teacherLinkDtoList = new ArrayList<>();
    List<TeacherLink> teacherLinks = teacherProfile.getLinks();
    if (teacherLinks != null) {
      for (TeacherLink teacherLink : teacherLinks) {
        teacherLinkDtoList.add(ListTeacherLinkResponse.from(teacherLink));
      }
    }

    return RetrieveTeacherProfileResponse.builder()
        .keyphrase(teacherProfile.getKeyphrase())
        .description(teacherProfile.getDescription())
        .major(Optional.ofNullable(teacherProfile.getMajor()).map(MajorResponse::from).orElse(null))
        .careers(teacherCareerDtoList)
        .links(teacherLinkDtoList)
        .build();
  }
}
