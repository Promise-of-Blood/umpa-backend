package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class MrProductionServicePostDetailResponse {

  private long id;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  private String additionalCostPolicy;

  private AverageDurationDto averageDuration;

  private int freeRevisionCount;

  private String softwareUsed;

  private List<SampleMrUrlResponse> sampleUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static MrProductionServicePostDetailResponse of(
    MrProductionServicePost mrProductionServicePost) {

    return MrProductionServicePostDetailResponse.builder().id(mrProductionServicePost.getId())
      .title(mrProductionServicePost.getTitle())
      .description(mrProductionServicePost.getDescription())
      .costPerUnit(CostPerUnitDto.from(mrProductionServicePost.getServiceCost()))
      .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy())
      .averageDuration(AverageDurationDto.from(mrProductionServicePost.getAverageDuration()))
      .freeRevisionCount(mrProductionServicePost.getFreeRevisionCount())
      .softwareUsed(mrProductionServicePost.getSoftwareUsed())
      .sampleUrls(SampleMrUrlResponse.fromList(mrProductionServicePost.getSampleMrUrls()))
      .teacherProfile(TeacherAuthorProfileDto.from(mrProductionServicePost.getUser()))
      .reviewRating(0.0f).build();
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class SampleMrUrlResponse {

    @Schema(description = "샘플 MR URL", example = "https://example.com/sample1.mp3")
    @NotBlank
    private String url;

    public static SampleMrUrlResponse from(SampleMrUrl sampleMrUrl) {
      return new SampleMrUrlResponse(sampleMrUrl.getUrl());
    }

    public static List<SampleMrUrlResponse> fromList(List<SampleMrUrl> sampleMrUrls) {
      return sampleMrUrls.stream().map(SampleMrUrlResponse::from).toList();
    }
  }
}
