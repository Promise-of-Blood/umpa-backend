package promiseofblood.umpabackend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.TeacherProfileDto.TeacherCareerDto;

public class ServicePostDto {

  /** 선생님 프로필 응답 형식 DTO */
  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class TeacherAuthorProfileDto {

    private String profileImageUrl;

    private String description;

    private List<TeacherProfileDto.TeacherCareerDto> careers;

    private List<TeacherProfileDto.TeacherLinkDto> links;

    public static TeacherAuthorProfileDto from(User user) {

      List<TeacherProfileDto.TeacherCareerDto> careers = new ArrayList<>();
      for (TeacherCareer career : user.getTeacherProfile().getCareers()) {
        careers.add(TeacherCareerDto.from(career));
      }

      List<TeacherProfileDto.TeacherLinkDto> links = new ArrayList<>();
      for (TeacherLink link : user.getTeacherProfile().getLinks()) {
        links.add(TeacherProfileDto.TeacherLinkDto.from(link));
      }

      return TeacherAuthorProfileDto.builder()
          .profileImageUrl(user.getProfileImageUrl())
          .description(user.getTeacherProfile().getDescription())
          .careers(careers)
          .links(links)
          .build();
    }
  }

  /** 서비스 단위당 비용 DTO */
  @Getter
  @AllArgsConstructor
  public static class CostPerUnitDto {

    private int cost;

    private String unit;

    public static CostPerUnitDto from(ServiceCost serviceCost) {
      return new CostPerUnitDto(serviceCost.getCost(), serviceCost.getUnit());
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
