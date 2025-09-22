package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;

@Getter
@Builder
public class ListMrProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private AverageDurationDto averageDuration;

  private List<String> softwareList;

  private List<String> sampleMrUrls;

  private String teacherName;

  private float reviewRating;

  public static ListMrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

    List<String> sampleMrUrls =
        mrProductionServicePost.getSampleMrUrls().stream().map(SampleMrUrl::getUrl).toList();

    return ListMrProductionServicePostResponse.builder()
        .id(mrProductionServicePost.getId())
        .title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription())
        .serviceCost(ServiceCostResponse.from(mrProductionServicePost.getServiceCost()))
        .averageDuration(AverageDurationDto.from(mrProductionServicePost.getAverageDuration()))
        .softwareList(mrProductionServicePost.getUsingSoftwareList())
        .sampleMrUrls(sampleMrUrls)
        .teacherName(String.valueOf((mrProductionServicePost.getUser().getUsername())))
        .reviewRating(0.0f)
        .build();
  }
}
