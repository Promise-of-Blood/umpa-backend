package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@Getter
@AllArgsConstructor
public class CreateLessonServicePostRequest {

  @Schema(type = "string", format = "binary", description = "대표 사진")
  @NotNull private final MultipartFile thumbnailImage;

  @Schema(description = "서비스 제목", example = "전문가의 레슨 서비스")
  private final String title;

  @Schema(
      description = "서비스 설명",
      example = "전문가가 직접 제작하는 고품질 MR 서비스입니다. 다양한 장르와 스타일을 지원하며, 맞춤형 제작이 가능합니다.")
  private final String description;

  @Schema(description = "시간당 레슨 비용", example = "50000")
  private final int cost;

  @Schema(description = "레슨 과목")
  private Subject subject;

  @Schema(description = "가능한 수업 지역들")
  private final List<Region> availableRegions;

  @Schema(description = "가능한 수업 요일들")
  private final List<WeekDay> availableWeekDays;

  @Schema(description = "수업 스타일")
  private final LessonStyle lessonStyle;

  @Schema(description = "데모 레슨 제공 여부", example = "true")
  private final Boolean isDemoLessonProvided;

  @Schema(description = "데모 레슨 비용", example = "20000")
  private final Integer demoLessonCost;

  @Schema(
      description = "추천 대상",
      example = "[\"시작은 하고 싶지만, 어디서부터 시작해야 할지 모르는 분들.\", \"기초부터 탄탄하게 배우고 싶은 분들.\"]")
  private final List<String> recommendedTargets;

  @ArraySchema(schema = @Schema(description = "연습실 사진 목록", type = "string", format = "binary"))
  private final List<MultipartFile> studioPhotos;
}
