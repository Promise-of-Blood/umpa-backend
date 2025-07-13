package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.dto.TeacherProfileDto;

@Getter
@Builder
public class MrProductionServicePostResponse {

  private String title;

  private String description;

  private TeacherProfileDto teacherProfile;

  private String reviewRating;

  private String costPerUnit;

  private String additionalCostPolicy;

  private String averageDuration;

  private String freeRevisionCount;

  private String softwareUsed;

  public static MrProductionServicePostResponse of(
    MrProductionServicePost mrProductionServicePost) {

    return MrProductionServicePostResponse.builder()
      .title(mrProductionServicePost.getTitle())
      .description(mrProductionServicePost.getDescription())

      .reviewRating(String.format("%.1f", 4.5)) // 예시로 4.5점으로 설정
      .costPerUnit(String.format("%,d원/%s",
        mrProductionServicePost.getServiceCost().getCost(),
        mrProductionServicePost.getServiceCost().getUnit()))

      .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy())

      .averageDuration(String.format("%d%s ~ %d%s",
        mrProductionServicePost.getAverageDuration().getMinValue(),
        mrProductionServicePost.getAverageDuration().getMinUnit(),
        mrProductionServicePost.getAverageDuration().getMaxValue(),
        mrProductionServicePost.getAverageDuration().getMaxUnit()
      ))

      .freeRevisionCount(String.valueOf(mrProductionServicePost.getFreeRevisionCount()))
      .softwareUsed(mrProductionServicePost.getSoftwareUsed())
      .build();
  }
}

