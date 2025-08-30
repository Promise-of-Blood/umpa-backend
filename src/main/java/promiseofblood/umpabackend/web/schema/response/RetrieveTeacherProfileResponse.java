package promiseofblood.umpabackend.web.schema.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class RetrieveTeacherProfileResponse {

  private String keyphrase;

  private String description;

  private ConstantResponses.MajorResponse major;

  private List<ListTeacherCareerResponse> careers;

  private List<ListTeacherLinkResponse> links;

  public static RetrieveTeacherProfileResponse from(TeacherProfile teacherProfile) {

    List<ListTeacherCareerResponse> listTeacherCareerResponseList = new ArrayList<>();
    for (TeacherCareer teacherCareer : teacherProfile.getCareers()) {
      listTeacherCareerResponseList.add(ListTeacherCareerResponse.from(teacherCareer));
    }

    List<ListTeacherLinkResponse> teacherLinkDtoList = new ArrayList<>();
    for (TeacherLink teacherLink : teacherProfile.getLinks()) {
      teacherLinkDtoList.add(ListTeacherLinkResponse.from(teacherLink));
    }

    return RetrieveTeacherProfileResponse.builder()
      .keyphrase(teacherProfile.getKeyphrase())
      .description(teacherProfile.getDescription())
      .major(ConstantResponses.MajorResponse.from(teacherProfile.getMajor()))
      .careers(listTeacherCareerResponseList)
      .links(teacherLinkDtoList)
      .build();
  }
}
