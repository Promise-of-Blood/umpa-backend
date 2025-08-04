package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;

public class MrProductionServicePostDto {

  @Getter
  @AllArgsConstructor
  public static class MrProductionServicePostRequest {

    @Schema(type = "string", format = "binary", description = "대표 사진")
    @NotBlank
    private MultipartFile thumbnailImage;

    private String title;

    private String description;

    // 제작 비용
    private int cost;

    // 추가비용정책
    private String additionalCostPolicy;

    private int freeRevisionCount;

    @Schema(description = "서비스 평균 소요 기간", example = "3WEEK~6MONTH")
    @Pattern(
      regexp = "^[0-9]+(DAY|WEEK|MONTH)~[0-9]+(DAY|WEEK|MONTH)$",
      message = "형식은 {숫자}{DAY|WEEK|MONTH}~{숫자}{DAY|WEEK|MONTH} 이어야 합니다."
    )
    private String averageDuration;

    private String softwareUsed;

    private List<String> sampleMrUrls;
  }

  @Getter
  @Builder
  public static class MrProductionServicePostResponse {

    private String title;

    private String description;

    private TeacherProfileDto teacherProfile;

    private String reviewRating;

    private String costPerUnit;

    private String additionalCostPolicy;

    private String averageDuration;

    private String freeRevisionCount;

    private String softwareUsed;

    private List<String> sampleUrls;

    public static MrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

      return MrProductionServicePostResponse.builder()
        .title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription())
        .teacherProfile(TeacherProfileDto.of(mrProductionServicePost.getUser().getTeacherProfile()))

        .reviewRating(String.format("%.1f", 0.0))
        .costPerUnit(String.format("%,d원/%s",
          mrProductionServicePost.getServiceCost().getCost(),
          mrProductionServicePost.getServiceCost().getUnit())
        )

        .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy())

        .averageDuration(String.format("%d%s ~ %d%s",
          mrProductionServicePost.getAverageDuration().getMinValue(),
          mrProductionServicePost.getAverageDuration().getMinUnit(),
          mrProductionServicePost.getAverageDuration().getMaxValue(),
          mrProductionServicePost.getAverageDuration().getMaxUnit()
        ))

        .freeRevisionCount(String.valueOf(mrProductionServicePost.getFreeRevisionCount()))
        .softwareUsed(mrProductionServicePost.getSoftwareUsed())
        .sampleUrls(mrProductionServicePost.getSampleMrUrls())
        .build();
    }
  }
}
