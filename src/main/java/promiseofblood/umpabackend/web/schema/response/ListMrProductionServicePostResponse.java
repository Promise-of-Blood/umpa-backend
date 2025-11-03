package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;

@Getter
@Builder
public class ListMrProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private RetrieveAverageDurationResponse averageDuration;

  private List<String> softwareList;

  private String teacherName;

  private float reviewRating;

  public static ListMrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

    return ListMrProductionServicePostResponse.builder()
        .id(mrProductionServicePost.getId())
        .title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription())
        .serviceCost(ServiceCostResponse.from(mrProductionServicePost.getServiceCost()))
        .averageDuration(
            RetrieveAverageDurationResponse.from(mrProductionServicePost.getAverageDuration()))
        .softwareList(
            mrProductionServicePost.getUsingSoftwareList() != null
                ? mrProductionServicePost.getUsingSoftwareList()
                : List.of())
        .teacherName(String.valueOf((mrProductionServicePost.getUser().getUsername())))
        .reviewRating(0.0f)
        .build();
  }
}
