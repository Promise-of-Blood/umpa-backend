package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveMrProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private Integer freeRevisionCount;

  private Integer additionalRevisionCost;

  private List<String> softwareList;

  private List<String> sampleMrUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveMrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

    List<String> sampleMrUrls =
        mrProductionServicePost.getSampleMrUrls().stream().map(SampleMrUrl::getUrl).toList();

    return RetrieveMrProductionServicePostResponse.builder()
        .id(mrProductionServicePost.getId())
        .title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription())
        .serviceCost(ServiceCostResponse.from(mrProductionServicePost.getServiceCost()))
        .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy())
        .averageDuration(AverageDurationDto.from(mrProductionServicePost.getAverageDuration()))
        .freeRevisionCount(mrProductionServicePost.getFreeRevisionCount())
        .additionalRevisionCost(mrProductionServicePost.getAdditionalRevisionCost())
        .softwareList(mrProductionServicePost.getUsingSoftwareList())
        .sampleMrUrls(sampleMrUrls)
        .teacherProfile(TeacherAuthorProfileDto.from(mrProductionServicePost.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
