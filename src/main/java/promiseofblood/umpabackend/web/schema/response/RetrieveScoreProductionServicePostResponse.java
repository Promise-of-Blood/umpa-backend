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
public class RetrieveScoreProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private List<CostPerUnitDto> costsPerUnits;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private int freeRevisionCount;

  private int additionalRevisionCost;

  private List<String> softwareList;

  private String sampleScoreImageUrl;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveScoreProductionServicePostResponse from(ScoreProductionServicePost post) {

    List<CostPerUnitDto> costPerUnits = new ArrayList<>();
    for (ServiceCost serviceCost : post.getServiceCosts()) {
      costPerUnits.add(CostPerUnitDto.from(serviceCost));
    }

    return RetrieveScoreProductionServicePostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .description(post.getDescription())
        .costsPerUnits(costPerUnits)
        .additionalCostPolicy(post.getAdditionalCostPolicy())
        .averageDuration(AverageDurationDto.from(post.getAverageDuration()))
        .freeRevisionCount(post.getFreeRevisionCount())
        .softwareList(post.getUsingSoftwareList())
        .additionalRevisionCost(post.getAdditionalRevisionCost())
        .sampleScoreImageUrl(post.getSampleScoreImageUrl())
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating(0.1f)
        .build();
  }
}
