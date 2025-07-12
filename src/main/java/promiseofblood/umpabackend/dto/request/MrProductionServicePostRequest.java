package promiseofblood.umpabackend.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MrProductionServicePostRequest {

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
