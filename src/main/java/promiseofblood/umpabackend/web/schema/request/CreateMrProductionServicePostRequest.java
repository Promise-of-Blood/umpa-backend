package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CreateMrProductionServicePostRequest {

  @Schema(type = "string", format = "binary", description = "대표 사진")
  @NotBlank
  private final MultipartFile thumbnailImage;

  @Schema(description = "서비스 제목", example = "전문가의 MR 제작 서비스")
  private final String title;

  @Schema(
      description = "서비스 설명",
      example = "전문가가 직접 제작하는 고품질 MR 서비스입니다. 다양한 장르와 스타일을 지원하며, 맞춤형 제작이 가능합니다.")
  private final String description;

  @Schema(description = "제작 비용", example = "50000")
  private final int cost;

  @Schema(description = "추가비용정책", example = "무료수정 이후 추가수정 요청 시 1만원 추가")
  private final String additionalCostPolicy;

  @Schema(description = "무료 수정 횟수", example = "2")
  private final int freeRevisionCount;

  @Schema(type = "integer", description = "추가 수정 비용")
  private final int additionalRevisionCost;

  @Schema(description = "서비스 평균 소요 기간", example = "3WEEK~6MONTH")
  @Pattern(
      regexp = "^[0-9]+(DAY|WEEK|MONTH)~[0-9]+(DAY|WEEK|MONTH)$",
      message = "형식은 {숫자}{DAY|WEEK|MONTH}~{숫자}{DAY|WEEK|MONTH} 이어야 합니다.")
  private final String averageDuration;

  @Schema(description = "사용 소프트웨어", example = "[\"Logic Pro X\", \"Ableton Live\"]")
  private final List<String> softwareList;

  @Schema(
      description = "샘플 MR URL 목록",
      example = "[\"https://example.com/sample1.mp3\", \"https://example.com/sample2.mp3\"]")
  private final List<String> sampleMrUrls;
}
