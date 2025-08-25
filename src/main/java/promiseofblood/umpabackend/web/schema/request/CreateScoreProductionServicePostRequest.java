package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CreateScoreProductionServicePostRequest {

  @Schema(type = "string", format = "binary", description = "대표 사진")
  @NotBlank
  private MultipartFile thumbnailImage;

  @Schema(description = "서비스 제목", example = "전문가의 악보 제작 서비스")
  private String title;

  @Schema(
    description = "서비스 설명",
    example = "전문가가 직접 제작하는 고품질 악보 서비스입니다. 다양한 장르와 스타일을 지원하며, 맞춤형 제작이 가능합니다.")
  private String description;

  @Schema(
    description = "악보 종류별 가격",
    example = "FULL_SCORE:50000,VOCAL:30000,PIANO:40000"
  )
  @Pattern(
    regexp = "^(FULL_SCORE|VOCAL|PIANO|GUITAR|BASS|WIND_INSTRUMENT|DRUM)(-[0-9]+)+$",
    message = "형식은 {악보종류}:{가격} 을 쉼표로 구분한 문자열이어야 합니다. 예: FULL_SCORE:50000,VOCAL:30000,PIANO:40000"
  )
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

  @Schema(description = "사용 소프트웨어", example = "Logic Pro X")
  private String softwareUsed;

  @Schema(type = "string", format = "binary", description = "샘플 악보 사진")
  @NotBlank
  private MultipartFile sampleScoreImage;

}
