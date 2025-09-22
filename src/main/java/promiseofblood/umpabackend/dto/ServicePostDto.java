package promiseofblood.umpabackend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.web.schema.response.ListTeacherCareerResponse;
import promiseofblood.umpabackend.web.schema.response.ListTeacherLinkResponse;

public class ServicePostDto {

  /** 선생님 프로필 응답 형식 DTO */
  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class TeacherAuthorProfileDto {

    private String keyphrase;

    private String profileImageUrl;

    private String description;

    private List<ListTeacherCareerResponse> careers;

    private List<ListTeacherLinkResponse> links;

    public static TeacherAuthorProfileDto from(User user) {

      TeacherProfile teacherProfile = user.getTeacherProfile();

      List<ListTeacherCareerResponse> careers = new ArrayList<>();
      for (TeacherCareer career : teacherProfile.getCareers()) {
        careers.add(ListTeacherCareerResponse.from(career));
      }

      List<ListTeacherLinkResponse> links = new ArrayList<>();
      for (TeacherLink link : teacherProfile.getLinks()) {
        links.add(ListTeacherLinkResponse.from(link));
      }

      return TeacherAuthorProfileDto.builder()
          .keyphrase(teacherProfile.getKeyphrase())
          .profileImageUrl(user.getProfileImageUrl())
          .description(teacherProfile.getDescription())
          .careers(careers)
          .links(links)
          .build();
    }
  }

  /** 서비스 평균 소요 기간 DTO */
  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class AverageDurationDto {

    private int minValue;

    private String minUnit;

    private int maxValue;

    private String maxUnit;

    public static AverageDurationDto from(DurationRange durationRange) {
      return AverageDurationDto.builder()
          .minValue(durationRange.getMinValue())
          .minUnit(durationRange.getMinUnit().name())
          .maxValue(durationRange.getMaxValue())
          .maxUnit(durationRange.getMaxUnit().name())
          .build();
    }
  }

  @Getter
  @Builder
  public static class ServicePostResponse {

    private Long id;

    private String title;

    private List<String> tags;

    private String teacherName;

    private String thumbnailImageUrl;

    private String costAndUnit;

    private Float reviewRating;
  }
}
