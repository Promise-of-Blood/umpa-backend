package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.dto.ServicePostDto.AverageDurationDto;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
public class ScoreProductionServicePostDto {

  @Getter
  @AllArgsConstructor
  public static class ScoreProductionServicePosRequest {

    @Schema(type = "string", format = "binary", description = "대표 사진")
    @NotBlank
    private MultipartFile thumbnailImage;

    @Schema(description = "서비스 제목", example = "전문가의 악보 제작 서비스")
    private String title;

    @Schema(
        description = "서비스 설명",
        example = "전문가가 직접 제작하는 고품질 악보 서비스입니다. 다양한 장르와 스타일을 지원하며, 맞춤형 제작이 가능합니다.")
    private String description;

    @Schema(description = "악보 종류별 가격", example = "FULL_SCORE:50000,VOCAL:30000,PIANO:40000")
    @Pattern(
        regexp = "^(FULL_SCORE|VOCAL|PIANO|GUITAR|BASS|WIND_INSTRUMENT|DRUM)(-[0-9]+)+$",
        message =
            "형식은 {악보종류}:{가격} 을 쉼표로 구분한 문자열이어야 합니다. 예: FULL_SCORE:50000,VOCAL:30000,PIANO:40000")
    private String costByScoreType;

    @Schema(description = "추가비용정책", example = "무료수정 이후 추가수정 요청 시 1만원 추가")
    private String additionalCostPolicy;

    @Schema(description = "무료 수정 횟수", example = "2")
    private int freeRevisionCount;

    @Schema(description = "추가 수정 비용", example = "10000")
    private int additionalRevisionCost;

    @Schema(description = "서비스 평균 소요 기간", example = "3WEEK~6MONTH")
    @Pattern(
        regexp = "^[0-9]+(DAY|WEEK|MONTH)~[0-9]+(DAY|WEEK|MONTH)$",
        message = "형식은 {숫자}{DAY|WEEK|MONTH}~{숫자}{DAY|WEEK|MONTH} 이어야 합니다.")
    private String averageDuration;

    @Schema(description = "사용 소프트웨어", example = "[\"Sibelius\", \"Finale\"]")
    private List<String> softwareList;

    @Schema(type = "string", format = "binary", description = "샘플 악보 사진")
    @NotBlank
    private MultipartFile sampleScoreImage;
  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class ScoreProductionServicePostResponse {

    private long id;

    private String title;

    private String description;

    private List<CostPerUnitDto> costsPerUnits;

    private String additionalCostPolicy;

    private AverageDurationDto averageDuration;

    private int freeRevisionCount;

    private List<String> softwareList;

    private int additionalRevisionCost;

    private String sampleScoreImageUrl;

    private TeacherAuthorProfileDto teacherProfile;

    private float reviewRating;

    public static ScoreProductionServicePostResponse from(
        ScoreProductionServicePost scoreProductionServicePost) {

      List<CostPerUnitDto> costPerUnits = new ArrayList<>();
      for (ServiceCost serviceCost : scoreProductionServicePost.getServiceCosts()) {
        costPerUnits.add(CostPerUnitDto.from(serviceCost));
      }

      return ScoreProductionServicePostResponse.builder()
          .id(scoreProductionServicePost.getId())
          .title(scoreProductionServicePost.getTitle())
          .description(scoreProductionServicePost.getDescription())
          .costsPerUnits(costPerUnits)
          .additionalCostPolicy(scoreProductionServicePost.getAdditionalCostPolicy())
          .averageDuration(AverageDurationDto.from(scoreProductionServicePost.getAverageDuration()))
          .freeRevisionCount(scoreProductionServicePost.getFreeRevisionCount())
          .softwareList(scoreProductionServicePost.getUsingSoftwareList())
          .additionalRevisionCost(scoreProductionServicePost.getAdditionalRevisionCost())
          .sampleScoreImageUrl(scoreProductionServicePost.getSampleScoreImageUrl())
          .teacherProfile(TeacherAuthorProfileDto.from(scoreProductionServicePost.getUser()))
          .reviewRating(0.1f)
          .build();
    }
  }
}
