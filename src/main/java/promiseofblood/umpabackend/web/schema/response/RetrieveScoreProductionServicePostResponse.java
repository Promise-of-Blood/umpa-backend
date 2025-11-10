package promiseofblood.umpabackend.web.schema.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.SampleScoreImageUrl;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.vo.PostDisplayStatus;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveScoreProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ConstantResponse<PostDisplayStatus> displayStatus;

  private List<ServiceCostResponse> serviceCostList;

  private String additionalCostPolicy;

  private RetrieveAverageDurationResponse averageDuration;

  private int freeRevisionCount;

  private int additionalRevisionCost;

  private List<String> softwareList;

  private List<String> sampleScoreImageUrls;

  private RetrieveTeacherAuthorProfileResponse teacherProfile;

  private float reviewRating;

  public static RetrieveScoreProductionServicePostResponse from(
      ScoreProductionServicePost scoreProductionServicePost) {

    List<ServiceCostResponse> costPerUnits = new ArrayList<>();
    for (ServiceCost serviceCost : scoreProductionServicePost.getServiceCosts()) {
      costPerUnits.add(ServiceCostResponse.from(serviceCost));
    }

    List<String> usingSoftwareList =
        scoreProductionServicePost.getUsingSoftwareList() != null
            ? scoreProductionServicePost.getUsingSoftwareList()
            : List.of();

    List<String> sampleScoreImageUrls = new ArrayList<>();
    for (SampleScoreImageUrl sampleScoreImageUrl :
        scoreProductionServicePost.getSampleScoreImageUrls()) {
      sampleScoreImageUrls.add(sampleScoreImageUrl.getUrl());
    }

    return RetrieveScoreProductionServicePostResponse.builder()
        .id(scoreProductionServicePost.getId())
        .title(scoreProductionServicePost.getTitle())
        .description(scoreProductionServicePost.getDescription())
        .displayStatus(new ConstantResponse<>(scoreProductionServicePost.getDisplayStatus()))
        .serviceCostList(costPerUnits)
        .additionalCostPolicy(scoreProductionServicePost.getAdditionalCostPolicy())
        .averageDuration(
            RetrieveAverageDurationResponse.from(scoreProductionServicePost.getAverageDuration()))
        .freeRevisionCount(scoreProductionServicePost.getFreeRevisionCount())
        .softwareList(usingSoftwareList)
        .additionalRevisionCost(scoreProductionServicePost.getAdditionalRevisionCost())
        .sampleScoreImageUrls(sampleScoreImageUrls)
        .teacherProfile(
            RetrieveTeacherAuthorProfileResponse.from(scoreProductionServicePost.getUser()))
        .reviewRating(0.1f)
        .build();
  }
}
