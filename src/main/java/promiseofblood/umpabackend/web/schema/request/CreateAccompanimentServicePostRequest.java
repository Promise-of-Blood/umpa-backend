package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.Instrument;

@Getter
@AllArgsConstructor
public class CreateAccompanimentServicePostRequest {

  @Schema(type = "string", format = "binary", description = "대표 사진")
  @NotBlank
  private MultipartFile thumbnailImage;

  @Schema(type = "string", description = "서비스 제목")
  @NotBlank
  private String title;

  @Schema(type = "string", description = "서비스 설명")
  @NotBlank
  private String description;

  @Schema(type = "integer", description = "서비스 비용")
  private int cost;

  @Schema(type = "string", description = "추가 비용 정책")
  @NotBlank
  private String additionalCostPolicy;

  @Schema(description = "반주 악기 종류")
  private Instrument instrument;

  @Schema(type = "integer", description = "포함된 연습 횟수")
  private int includedPracticeCount;

  @Schema(type = "integer", description = "추가 연습 비용")
  private int additionalPracticeCost;

  @Schema(description = "MR 포함 여부")
  private Boolean isMrIncluded;

  @Schema(type = "string", description = "연습 장소")
  private String practiceLocation;

  @Schema(type = "array", description = "연주 영상 URL들")
  private List<String> videoUrls;
}
