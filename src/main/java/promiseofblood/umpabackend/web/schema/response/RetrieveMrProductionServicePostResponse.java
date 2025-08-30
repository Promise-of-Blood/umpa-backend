package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveMrProductionServicePostResponse {

  private long id;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private int freeRevisionCount;

  private int additionalRevisionCost;

  private List<String> softwareList;

  private List<SampleMrUrlResponse> sampleUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveMrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

    return RetrieveMrProductionServicePostResponse.builder()
        .id(mrProductionServicePost.getId())
        .title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription())
        .costPerUnit(CostPerUnitDto.from(mrProductionServicePost.getServiceCost()))
        .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy())
        .averageDuration(AverageDurationDto.from(mrProductionServicePost.getAverageDuration()))
        .freeRevisionCount(mrProductionServicePost.getFreeRevisionCount())
        .additionalRevisionCost(mrProductionServicePost.getAdditionalRevisionCost())
        .softwareList(mrProductionServicePost.getUsingSoftwareList())
        .sampleUrls(SampleMrUrlResponse.fromList(mrProductionServicePost.getSampleMrUrls()))
        .teacherProfile(TeacherAuthorProfileDto.from(mrProductionServicePost.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
