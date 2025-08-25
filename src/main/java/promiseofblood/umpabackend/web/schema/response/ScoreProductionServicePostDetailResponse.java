package promiseofblood.umpabackend.web.schema.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ScoreProductionServicePostDetailResponse {

  private long id;

  private String title;

  private String description;

  private List<CostPerUnitDto> costsPerUnits;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private int freeRevisionCount;

  private String softwareUsed;

  private int additionalRevisionCost;

  private String sampleScoreImageUrl;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static ScoreProductionServicePostDetailResponse from(
    ScoreProductionServicePost scoreProductionServicePost) {

    List<CostPerUnitDto> costPerUnits = new ArrayList<>();
    for (ServiceCost serviceCost : scoreProductionServicePost.getServiceCosts()) {
      costPerUnits.add(CostPerUnitDto.from(serviceCost));
    }

    return ScoreProductionServicePostDetailResponse.builder()
      .id(scoreProductionServicePost.getId())
      .title(scoreProductionServicePost.getTitle())
      .description(scoreProductionServicePost.getDescription())
      .costsPerUnits(costPerUnits)
      .additionalCostPolicy(scoreProductionServicePost.getAdditionalCostPolicy())
      .averageDuration(AverageDurationDto.from(scoreProductionServicePost.getAverageDuration()))
      .freeRevisionCount(scoreProductionServicePost.getFreeRevisionCount())
      .softwareUsed(scoreProductionServicePost.getSoftwareUsed())
      .additionalRevisionCost(scoreProductionServicePost.getAdditionalRevisionCost())
      .sampleScoreImageUrl(scoreProductionServicePost.getSampleScoreImageUrl())
      .teacherProfile(TeacherAuthorProfileDto.from(scoreProductionServicePost.getUser()))
      .reviewRating(0.1f)
      .build();
  }

}
