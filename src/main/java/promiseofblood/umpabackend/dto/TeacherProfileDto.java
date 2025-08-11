package promiseofblood.umpabackend.dto;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.dto.ConstantDto.MajorResponse;
import promiseofblood.umpabackend.dto.response.RegionResponse;


public class TeacherProfileDto {

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherProfileResponse {

    private String description;

    private MajorResponse major;

    private RegionResponse lessonRegion;

    private List<TeacherCareerDto> careers;

    private List<TeacherLinkDto> links;

    public static TeacherProfileResponse from(TeacherProfile teacherProfile) {

      List<TeacherCareerDto> teacherCareerDtoList = new ArrayList<>();
      for (TeacherCareer teacherCareer : teacherProfile.getCareers()) {
        teacherCareerDtoList.add(TeacherCareerDto.from(teacherCareer));
      }

      List<TeacherLinkDto> teacherLinkDtoList = new ArrayList<>();
      for (TeacherLink teacherLink : teacherProfile.getLinks()) {
        teacherLinkDtoList.add(TeacherLinkDto.from(teacherLink));
      }

      return TeacherProfileResponse.builder()
        .description(teacherProfile.getDescription())
        .major(MajorResponse.from(teacherProfile.getMajor()))
        .lessonRegion(RegionResponse.from(teacherProfile.getLessonRegion()))
        .careers(teacherCareerDtoList)
        .links(teacherLinkDtoList)
        .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherCareerDto {

    private long id;

    private boolean isRepresentative;

    private String title;

    private YearMonth start;

    private YearMonth end;

    public static TeacherCareerDto from(TeacherCareer teacherCareer) {
      return TeacherCareerDto.builder()
        .id(teacherCareer.getId())
        .isRepresentative(teacherCareer.isRepresentative())
        .title(teacherCareer.getTitle())
        .start(teacherCareer.getStart())
        .end(teacherCareer.getEnd())
        .build();
    }
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  @ToString
  public static class TeacherLinkDto {

    private long id;

    private String link;

    public static TeacherLinkDto from(TeacherLink teacherLink) {
      return TeacherLinkDto.builder()
        .id(teacherLink.getId())
        .link(teacherLink.getLink())
        .build();
    }
  }

}
