package promiseofblood.umpabackend.web.schema.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ListScoreProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private List<ServiceCostResponse> serviceCostList;

  private RetrieveAverageDurationResponse averageDuration;

  private List<String> softwareList;

  private String teacherName;

  private float reviewRating;

  public static ListScoreProductionServicePostResponse from(
      ScoreProductionServicePost scoreProductionServicePost) {

    List<ServiceCostResponse> costPerUnits = new ArrayList<>();
    for (ServiceCost serviceCost : scoreProductionServicePost.getServiceCosts()) {
      costPerUnits.add(ServiceCostResponse.from(serviceCost));
    }
    System.out.println(scoreProductionServicePost.getAverageDuration());
    return ListScoreProductionServicePostResponse.builder()
        .id(scoreProductionServicePost.getId())
        .title(scoreProductionServicePost.getTitle())
        .description(scoreProductionServicePost.getDescription())
        .serviceCostList(costPerUnits)
        .averageDuration(
            RetrieveAverageDurationResponse.from(scoreProductionServicePost.getAverageDuration()))
        .softwareList(
            scoreProductionServicePost.getUsingSoftwareList() != null
                ? scoreProductionServicePost.getUsingSoftwareList()
                : List.of())
        .teacherName(String.valueOf((scoreProductionServicePost.getUser().getUsername())))
        .reviewRating(3.1f)
        .build();
  }
}
