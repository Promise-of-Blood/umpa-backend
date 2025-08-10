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

    @Schema(description = "서비스 제목", example = "전문가의 MR 제작 서비스")
    private String title;

    @Schema(description = "서비스 설명", example = "전문가가 직접 제작하는 고품질 MR 서비스입니다. 다양한 장르와 스타일을 지원하며, 맞춤형 제작이 가능합니다.")
    private String description;

    @Schema(description = "제작 비용", example = "50000")
    private int cost;

    @Schema(description = "추가비용정책", example = "무료수정 이후 추가수정 요청 시 1만원 추가")
    private String additionalCostPolicy;

    @Schema(description = "무료 수정 횟수", example = "2")
    private int freeRevisionCount;

    @Schema(description = "서비스 평균 소요 기간", example = "3WEEK~6MONTH")
    @Pattern(regexp = "^[0-9]+(DAY|WEEK|MONTH)~[0-9]+(DAY|WEEK|MONTH)$", message = "형식은 {숫자}{DAY|WEEK|MONTH}~{숫자}{DAY|WEEK|MONTH} 이어야 합니다.")
    private String averageDuration;

    @Schema(description = "사용 소프트웨어", example = "Logic Pro X")
    private String softwareUsed;

    @Schema(description = "샘플 MR URL 목록", example = "[\"https://example.com/sample1.mp3\", \"https://example.com/sample2.mp3\"]")
    private List<String> sampleMrUrls;
  }

  @Getter
  @Builder
  public static class MrProductionServicePostResponse {

    private String title;

    private String description;

    private TeacherProfileDto.TeacherProfileResponse teacherProfile;

    private float reviewRating;

    private String costPerUnit;

    private String additionalCostPolicy;

    private String averageDuration;

    private int freeRevisionCount;

    private String softwareUsed;

    private List<String> sampleUrls;

    public static MrProductionServicePostResponse of(
      MrProductionServicePost mrProductionServicePost) {

      return MrProductionServicePostResponse.builder().title(mrProductionServicePost.getTitle())
        .description(mrProductionServicePost.getDescription()).teacherProfile(
          TeacherProfileDto.TeacherProfileResponse.from(
            mrProductionServicePost.getUser().getTeacherProfile())).reviewRating(0.0f).costPerUnit(
          String.format("%,d원/%s", mrProductionServicePost.getServiceCost().getCost(),
            mrProductionServicePost.getServiceCost().getUnit()))
        .additionalCostPolicy(mrProductionServicePost.getAdditionalCostPolicy()).averageDuration(
          String.format("%d%s ~ %d%s", mrProductionServicePost.getAverageDuration().getMinValue(),
            mrProductionServicePost.getAverageDuration().getMinUnit(),
            mrProductionServicePost.getAverageDuration().getMaxValue(),
            mrProductionServicePost.getAverageDuration().getMaxUnit()))
        .freeRevisionCount(mrProductionServicePost.getFreeRevisionCount())
        .softwareUsed(mrProductionServicePost.getSoftwareUsed())
        .sampleUrls(mrProductionServicePost.getSampleMrUrls()).build();
    }
  }
}
