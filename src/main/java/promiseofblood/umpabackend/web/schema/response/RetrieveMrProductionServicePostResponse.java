package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveMrProductionServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private Integer freeRevisionCount;

  private Integer additionalRevisionCost;

  private List<String> softwareList;

  private List<String> sampleMrUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveMrProductionServicePostResponse of(
      MrProductionServicePost post) {

    List<String> sampleMrUrls =
        post.getSampleMrUrls().stream().map(SampleMrUrl::getUrl).toList();

    return RetrieveMrProductionServicePostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .description(post.getDescription())
        .costPerUnit(CostPerUnitDto.from(post.getServiceCost()))
        .additionalCostPolicy(post.getAdditionalCostPolicy())
        .averageDuration(AverageDurationDto.from(post.getAverageDuration()))
        .freeRevisionCount(post.getFreeRevisionCount())
        .additionalRevisionCost(post.getAdditionalRevisionCost())
        .softwareList(post.getUsingSoftwareList())
        .sampleMrUrls(sampleMrUrls)
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
